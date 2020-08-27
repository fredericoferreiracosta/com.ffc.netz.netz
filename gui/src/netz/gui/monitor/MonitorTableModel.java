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

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.netz.traffic.model.PacketTO;

public class MonitorTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 5604298923589162593L;

	private ArrayList<PacketTO> list;
	
	public MonitorTableModel(ArrayList<PacketTO> list) {
		this.list = list;
	}
	
	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	
	@Override
	public Object getValueAt(int row, int column) {
		PacketTO model = list.get(row);
		switch (column) {
			case 0:
				return model.getSourceIp() + ":" + model.getSourcePort();
			case 1:
				return model.getDestinationIp() + ":" + model.getDestinationPort();
			case 2:
				return model.getProtocol();
			case 3:
				return model.getSourceMac();
			case 4:
				return model.getDestinationMac();
			case 5:
				return model.getPacketLength();
		}
		return "";
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "IP Origem";
			case 1:
				return "IP Destino";
			case 2:
				return "Protocolo";
			case 3:
				return "MAC Origem";
			case 4:
				return "MAC Destino";
			case 5:
				return "Tamanho Pacote (bytes)";
			}
		return "";
	}
	

	public void cleanTable(){
		int i = list.size();
		if(i>0){
			list.clear();
			fireTableRowsDeleted(0, i - 1);  
		}
	}

	public void filterTable(ArrayList<PacketTO> list) {
		this.list = list;
		fireTableDataChanged();
	}

	public void atualizaLista(ArrayList<PacketTO> list) {
		this.list.addAll(list);
		fireTableDataChanged();
	}
	
	public void addPacket(PacketTO packet){
		this.list.add(0, packet);
		fireTableDataChanged();
	}

	public void limparMonitor() {
		this.list = new ArrayList<PacketTO>();
		fireTableDataChanged();
	}
}
