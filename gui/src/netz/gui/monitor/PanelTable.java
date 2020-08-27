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

package netz.gui.monitor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;


import br.netz.traffic.model.PacketTO;

public class PanelTable extends JPanel{

	private static final long serialVersionUID = -5717477393522093333L;

	private JTable monitorTable;
	private JScrollPane scrollPane;
	private MonitorTableModel tableModel;
	private JLabel label;
	private JTextField filterText;
	private JComboBox filtercolumn;
	private int columnNumber;
	private TableRowSorter<MonitorTableModel> sorter;
	
	public PanelTable() {
		super();
		initialize();
	}

	private void initialize() {
		setPreferredSize(new Dimension(960,675));
		add(getLabel());
		add(getFilterText());
		add(getFiltercolumn());
		add(getScrollPane());
	}
	
	private JComboBox getFiltercolumn() {
		if(filtercolumn==null) {
			filtercolumn = new JComboBox(new String[]{"IP origem","IP destino","Protocolo","Mac origem","Mac destino","Tamanho do pacote"});
			filtercolumn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					columnNumber = filtercolumn.getSelectedIndex();
					newFilter();
				}
				
			});
		}
		return filtercolumn;
	}
	
	private JLabel getLabel() {
		if(label==null) {
			label = new JLabel();
			label.setText("Filtro ");
		}
		return label;
	}

	private JTextField getFilterText() {
		if(filterText==null) {
			filterText = new JTextField();
			filterText.setColumns(30);
		    filterText.getDocument().addDocumentListener(new DocumentListener() {
		      public void changedUpdate(DocumentEvent e) {
		        newFilter();
		      }

		      public void insertUpdate(DocumentEvent e) {
		        newFilter();
		      }

		      public void removeUpdate(DocumentEvent e) {
		        newFilter();
		      }
			
		    });
		}
		return filterText;
	}

	private void newFilter() {
		RowFilter<MonitorTableModel, Object> rf = null;
	    try {
	      rf = RowFilter.regexFilter("(?i)" + filterText.getText(), columnNumber);
	    } catch (java.util.regex.PatternSyntaxException e) {
	      return;
	    }
	    getSorter().setRowFilter(rf);
	}
	
	

	private TableRowSorter<MonitorTableModel> getSorter() {
		if(sorter==null) {
			sorter = new TableRowSorter<MonitorTableModel>(tableModel);
		}
		return sorter;
	}

	private JTable getMonitorTable() {
		if(monitorTable==null){
			monitorTable = new JTable();
			monitorTable.setModel(getTableModel());
			monitorTable.getColumnModel().getColumn(0).setPreferredWidth(175);
			monitorTable.getColumnModel().getColumn(1).setPreferredWidth(175);
			monitorTable.getColumnModel().getColumn(2).setPreferredWidth(175);
			monitorTable.getColumnModel().getColumn(3).setPreferredWidth(175);
			monitorTable.getColumnModel().getColumn(4).setPreferredWidth(150);
			monitorTable.getColumnModel().getColumn(5).setPreferredWidth(150);
			monitorTable.setRowSorter(getSorter());
		}
		return monitorTable;
	}

	private JScrollPane getScrollPane() {
		if(scrollPane==null){
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getMonitorTable());
			scrollPane.setPreferredSize(new Dimension(960,630));
		}
		
		return scrollPane;
	}

	private MonitorTableModel getTableModel() {
		if(tableModel==null){
			tableModel = new MonitorTableModel(new ArrayList<PacketTO>());
		}
		return tableModel;
	}

	
	public void addMonitorPackets(ArrayList<PacketTO> packets) {
		getTableModel().atualizaLista(packets);
	}

	public void addPacket(PacketTO packet){
		getTableModel().addPacket(packet);
	}

	public void limparMonitor() {
		getTableModel().limparMonitor();
	}
	
}
