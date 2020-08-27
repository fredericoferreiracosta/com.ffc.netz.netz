/*
	NETZ - Network management support system
    Copyright (C) 2011  Alana de Almeida Brand�o (alana.brandao@yahoo.com.br)
    					Frederico Ferreira Costa (fredericoferreira@live.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package br.netz.graphic.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.graphic.controller.DataGenerator;
import br.netz.graphic.controller.PdfManager;

public class GraphicPanel extends JPanel {

	private static final long serialVersionUID = -8305757887118881234L;
	private Graphic graphic;
	private Buttons buttons;

	public GraphicPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(getGraphic());
		add(getButtons());
		setBorder(new TitledBorder(BorderFactory.createTitledBorder("Tr�fego de pacotes")));
	}

	private Graphic getGraphic() {
		if (graphic == null) {
			try {
				graphic = new Graphic();
			} catch (ConfigurationDAOException e) {
				e.printStackTrace();
			}
		}
		return graphic;
	}

	private Buttons getButtons() {
		if (buttons == null) {
			buttons = new Buttons();
		}
		return buttons;
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("Gr�fico");
		GraphicPanel panel = new GraphicPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setBounds(200, 120, 650, 420);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public class Graphic extends JPanel {

		private static final long serialVersionUID = 6109952328835221268L;

		private TimeSeries size;
		private JFreeChart chart;
		private ConfigurationDAO configurationDAO;

		public Graphic() throws ConfigurationDAOException {
			super();
			initialize();
			setPreferredSize(new Dimension(600, 350));
			configurationDAO = new ConfigurationDAO();
			GeneralConfigurationTO configurationTO = new GeneralConfigurationTO();
			configurationTO = configurationDAO.getGeneralConfiguration();
			new DataGenerator(configurationTO.getGraphicUpdate() * 1000, this).start();
		}

		public JFreeChart getChart() {
			return chart;
		}

		public void initialize() {
			TimeSeriesCollection dataset = new TimeSeriesCollection();

			this.size = new TimeSeries("Trafego (MB)");
			this.size.setMaximumItemAge(300000);

			dataset.addSeries(this.size);

			DateAxis domain = new DateAxis("Tempo");
			NumberAxis range = new NumberAxis("Trafego (MB)");
			domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
			range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
			domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
			range.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));

			XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
			renderer.setSeriesPaint(0, Color.red);
			XYPlot plot = new XYPlot(dataset, domain, range, renderer);
			plot.setBackgroundPaint(Color.white);
			plot.setDomainGridlinePaint(Color.lightGray);
			plot.setRangeGridlinePaint(Color.lightGray);
			domain.setAutoRange(true);

			range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			chart = new JFreeChart("Trafego", new Font("SansSerif", Font.BOLD,
					16), plot, true);
			chart.setBackgroundPaint(Color.white);
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(585, 340));
			chartPanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(1, 1, 1, 1), BorderFactory
							.createLineBorder(Color.black)));
			add(chartPanel);
			this.setDoubleBuffered(true);

		}

		public void addTraffic(float x) {
			this.size.addOrUpdate(new Millisecond(), x);
		}
	}

	public class Buttons extends JPanel {

		private static final long serialVersionUID = 6577657964942261498L;
		
		private JButton button;

		public Buttons() {
			add(getButton());
		}

		private JButton getButton() {
			if (button == null) {
				button = new JButton();
				URL caminho = getClass().getResource("/resources/salvar.png");
				button.setIcon(new ImageIcon(caminho));
				button.setPreferredSize(new Dimension(24, 24));
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						PdfManager pdfManager = new PdfManager();
						pdfManager.savePdf(getChart(), 800, 600);
					}
				});
			}
			return button;
		}

	}

	public JFreeChart getChart() {
		return getGraphic().getChart();
	}
}