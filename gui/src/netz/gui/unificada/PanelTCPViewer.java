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

package netz.gui.unificada;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import br.netz.traffic.model.ActiveConnectionsTO;

public class PanelTCPViewer extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable tcpViewerTable;
	private JScrollPane scrollPane;
	private TCPViewerTableModel tableModel;
	
	public PanelTCPViewer() {
		super();
		initialize();
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(970,260));
		add(getScrollPane());
		setBorder(new TitledBorder(BorderFactory.createTitledBorder("Visualizador de conex�es TCP")));
	}

	public JTable getTcpViewerTable() {
		if(tcpViewerTable==null){
			tcpViewerTable = new JTable();
			tcpViewerTable.setModel(getTableModel());
			tcpViewerTable.getColumnModel().getColumn(0).setPreferredWidth(175);
			tcpViewerTable.getColumnModel().getColumn(1).setPreferredWidth(175);
			tcpViewerTable.getColumnModel().getColumn(2).setPreferredWidth(275);
		}
		return tcpViewerTable;
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane==null){
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTcpViewerTable());
			scrollPane.setPreferredSize(new Dimension(965,215));
		}
		return scrollPane;
	}
	
	public TCPViewerTableModel getTableModel() {
		if(tableModel==null){
			tableModel = new TCPViewerTableModel(new ArrayList<ActiveConnectionsTO>());
		}
		return tableModel;
	}

	public void addTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
		getTableModel().addTcpViewer(activeConnectionsTO);
	}

	public void removeTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
		getTableModel().removeTcpViewer(activeConnectionsTO);
	}
}
