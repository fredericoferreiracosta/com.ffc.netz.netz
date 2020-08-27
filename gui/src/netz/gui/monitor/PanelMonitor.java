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
import java.util.ArrayList;

import javax.swing.JPanel;

import br.netz.traffic.model.PacketTO;

public class PanelMonitor extends JPanel{

	private static final long serialVersionUID = -7226186418198769106L;

	private PanelTable panelTable;
	
	public PanelMonitor() {
		super();
		initialize();
	}

	private void initialize() {
		setPreferredSize(new Dimension(980,675));
		add(getPanelTable());
	}

	private PanelTable getPanelTable() {
		if(panelTable==null){
			panelTable = new PanelTable();
		}
		return panelTable;
	}

	public void addMonitorPackages(ArrayList<PacketTO> packets) {
		getPanelTable().addMonitorPackets(packets);
	}
	
	public void addPacket(PacketTO packet){
		getPanelTable().addPacket(packet);
	}

	public void limparMonitor() {
		getPanelTable().limparMonitor();
	}
}
