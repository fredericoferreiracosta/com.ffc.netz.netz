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

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.netz.traffic.model.ActiveConnectionsTO;

public class TCPViewerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<ActiveConnectionsTO> list;
	
	public TCPViewerTableModel(ArrayList<ActiveConnectionsTO> arrayList) {
		this.list = arrayList;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		ActiveConnectionsTO model = list.get(row);
		switch (column) {
			case 0:
				return model.getSrc() + ":" + model.getSrcPort();
			case 1:
				return model.getDst() + ":" + model.getDstPort();
			case 2:
				return model.getStatus();
		}
		return "";
	}
	
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Origem";
			case 1:
				return "Destino";
			case 2:
				return "Estado da conex�o";
			}
		return "";
	}

	public void addTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
		this.list.add(0, activeConnectionsTO);
		fireTableDataChanged();
	}
	
	public void removeTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
		this.list.remove(activeConnectionsTO);
		fireTableDataChanged();
	}

}
