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

package netz.gui.configuracoes;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import netz.gui.listeners.FrameConfSmsListener;
import br.netz.configuration.model.SmsConfigurationTO;

public class FrameConfSms extends JFrame {

	private static final long serialVersionUID = -7705111101435547313L;

	private PanelConfSms panelConfSms;
	private ArrayList<FrameConfSmsListener> listeners = new ArrayList<FrameConfSmsListener>();

	public FrameConfSms() {
		super("Configura��es de SMS");
		add(getPanelConfSms());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	public PanelConfSms getPanelConfSms() {
		if (panelConfSms == null) {
			panelConfSms = new PanelConfSms();
		}
		return panelConfSms;
	}

	private class PanelConfSms extends JPanel {

		private static final long serialVersionUID = -932257807898852225L;
		private JLabel configuracoesSmsLabel;
		private JLabel urlLabel;
		private JTextArea urlTextField;
		private JScrollPane urlScrollPane;
		private JLabel mensagemLabel;

		private PanelConfSmsButtons panelButtons;

		private GridBagConstraints configuracoesSmsLabelConstraints;
		private GridBagConstraints urlLabelConstraints;
		private GridBagConstraints urlTextFieldConstraints;
		private GridBagConstraints mensagemLabelConstraints;

		private GridBagConstraints panelButtonsConstraints;

		public PanelConfSms() {
			initialize();
			setPreferredSize(new Dimension(300,320));
		}

		public void initialize() {
			setLayout(new GridBagLayout());
			add(getConfiguracoesSmsLabel(),
					getConfiguracoesSmsLabelConstraints());
			add(getUrlLabel(), getUrlLabelConstraints());
			add(getUrlScrollPane(), getUrlTextFieldConstraints());
			add(getMensagemLabel(), getMensagemLabelConstraints());
			add(getPanelButtons(), getPanelButtonsConstraints());
		}

		private JLabel getConfiguracoesSmsLabel() {
			if (configuracoesSmsLabel == null) {
				configuracoesSmsLabel = new JLabel();
				configuracoesSmsLabel.setText("Configura��es de Gateway SMS");
				configuracoesSmsLabel.setFont(configuracoesSmsLabel.getFont().deriveFont(
						configuracoesSmsLabel.getFont().getStyle() ^ Font.BOLD));
			}
			return configuracoesSmsLabel;
		}

		private JLabel getUrlLabel() {
			if (urlLabel == null) {
				urlLabel = new JLabel();
				urlLabel.setText("URL");
			}
			return urlLabel;
		}

		private JTextArea getUrlTextField() {
			if (urlTextField == null) {
				urlTextField = new JTextArea();
			}
			return urlTextField;
		}
		
	
		private JScrollPane getUrlScrollPane() {
			if(urlScrollPane==null){
				urlScrollPane = new JScrollPane(getUrlTextField());
				urlScrollPane.setPreferredSize(new Dimension(20, 40));
			}
			return urlScrollPane;
		}

		private JLabel getMensagemLabel() {
			if (mensagemLabel == null) {
				mensagemLabel = new JLabel();
				mensagemLabel
						.setText("<html>ATEN��O: Informe a URL do gateway contratado <br>" +
								" informando inclusive todos os seus dados de acesso <br>" +
								" como usu�rio, senha e n�mero do celular j� <br>" +
								" configurados. Coloque tamb�m o par�metro <br>" +
								" \"messagenetz\" onde o texto do SMS ficar� na URL.<br>" +
								" O Netz vai usar esse par�metro para saber onde colocar <br>" +
								" as informa��es. Ex: http://www.fastsms.com.br/sms<br>" +
								"?id=user&senha=password& para=35888****&<br>" +
								"texto=messagenetz</html>");
			}
			return mensagemLabel;
		}

		private PanelConfSmsButtons getPanelButtons() {
			if (panelButtons == null) {
				panelButtons = new PanelConfSmsButtons();
			}
			return panelButtons;
		}

		private GridBagConstraints getConfiguracoesSmsLabelConstraints() {
			if (configuracoesSmsLabelConstraints == null) {
				configuracoesSmsLabelConstraints = formatoGeral();
				configuracoesSmsLabelConstraints.gridx = 0;
				configuracoesSmsLabelConstraints.gridy = 0;
				configuracoesSmsLabelConstraints.gridwidth = 2;
				configuracoesSmsLabelConstraints.insets = new Insets(1, 1, 8, 1);
			}
			return configuracoesSmsLabelConstraints;
		}

		private GridBagConstraints getUrlLabelConstraints() {
			if (urlLabelConstraints == null) {
				urlLabelConstraints = formatoGeral();
				urlLabelConstraints.gridx = 0;
				urlLabelConstraints.gridy = 1;
				urlLabelConstraints.insets = new Insets(1, 1, 8, 1);
			}
			return urlLabelConstraints;
		}

		private GridBagConstraints getUrlTextFieldConstraints() {
			if (urlTextFieldConstraints == null) {
				urlTextFieldConstraints = formatoGeral();
				urlTextFieldConstraints.gridx = 1;
				urlTextFieldConstraints.gridy = 1;
			}
			return urlTextFieldConstraints;
		}

		private GridBagConstraints getMensagemLabelConstraints() {
			if (mensagemLabelConstraints == null) {
				mensagemLabelConstraints = formatoGeral();
				mensagemLabelConstraints.gridx = 0;
				mensagemLabelConstraints.gridy = 2;
				mensagemLabelConstraints.gridwidth = 2;
				mensagemLabelConstraints.insets = new Insets(1, 1, 8, 1);
			}
			return mensagemLabelConstraints;
		}

		private GridBagConstraints getPanelButtonsConstraints() {
			if (panelButtonsConstraints == null) {
				panelButtonsConstraints = formatoGeral();
				panelButtonsConstraints.gridx = 0;
				panelButtonsConstraints.gridy = 3;
				panelButtonsConstraints.gridwidth = 2;
			}
			return panelButtonsConstraints;
		}

		private GridBagConstraints formatoGeral() {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(1, 1, 1, 1);
			return gbc;
		}

		private class PanelConfSmsButtons extends JPanel {

			private static final long serialVersionUID = 8757785695907403278L;

			private PanelConfSmsButtons1 panel1;
			private PanelConfSmsButtons2 panel2;

			public PanelConfSmsButtons() {
				setLayout(new GridLayout(2, 1));
				add(getPanel1());
				add(getPanel2());
			}

			private PanelConfSmsButtons1 getPanel1() {
				if (panel1 == null) {
					panel1 = new PanelConfSmsButtons1();
				}
				return panel1;
			}

			private PanelConfSmsButtons2 getPanel2() {
				if (panel2 == null) {
					panel2 = new PanelConfSmsButtons2();
				}
				return panel2;
			}

			private class PanelConfSmsButtons1 extends JPanel {

				private static final long serialVersionUID = 1520885503708282072L;

				private JButton smsTesteButton;

				public PanelConfSmsButtons1() {
					add(getSmsTesteButton());
				}

				private JButton getSmsTesteButton() {
					if (smsTesteButton == null) {
						smsTesteButton = new JButton();
						smsTesteButton.setText("Enviar SMS de teste");
						smsTesteButton.addActionListener(new ActionListener(){
							
							@Override
							public void actionPerformed(ActionEvent e) {
								for(FrameConfSmsListener listener : listeners){
									listener.enviarSmsTeste(getConfiguracoesSms());
								}
							}
							
						});
					}
					return smsTesteButton;
				}

			}

			private class PanelConfSmsButtons2 extends JPanel {

				private static final long serialVersionUID = -2695598946015786339L;

				private JButton salvarButton;
				private JButton cancelarButton;

				public PanelConfSmsButtons2() {
					add(getSalvarButton());
					add(getCancelarButton());
				}

				private JButton getSalvarButton() {
					if (salvarButton == null) {
						salvarButton = new JButton();
						salvarButton.setText("Salvar");
						salvarButton.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								for(FrameConfSmsListener listener : listeners){
									listener.salvarConfiguracoesSms(getConfiguracoesSms());
								}
							}
							
						});
					}
					return salvarButton;
				}

				private JButton getCancelarButton() {
					if (cancelarButton == null) {
						cancelarButton = new JButton();
						cancelarButton.setText("Cancelar");
						cancelarButton.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								dispose();
							}
							
						});
					}
					return cancelarButton;
				}
			}

		}

		public void setConfigrations(SmsConfigurationTO smsConfigurationTO) {
			getUrlTextField().setText(smsConfigurationTO.getUrl());
		}
	}
	
	public void addFrameConfSmsListener(FrameConfSmsListener listener){
		listeners.add(listener);
	}
	
	public SmsConfigurationTO getConfiguracoesSms(){
		String url = getPanelConfSms().getUrlTextField().getText();
		
		SmsConfigurationTO smsConfiguration = new SmsConfigurationTO();
		smsConfiguration.setUrl(url);
		return smsConfiguration;
	}

	public void setConfigurations(SmsConfigurationTO smsConfigurationTO) {
		getPanelConfSms().setConfigrations(smsConfigurationTO);
	}

}
