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

package netz.gui.main;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import netz.gui.listeners.FramePrincipalListener;
import netz.gui.listeners.TabbedPanelListener;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import br.netz.hosts.model.HostTO;
import br.netz.traffic.model.ActiveConnectionsTO;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAOException;

public class FramePrincipal extends JFrame {

	private static final long serialVersionUID = 8139280054696966435L;
	
	private TabbedPanel tabbedPanel;
	
	private ArrayList<FramePrincipalListener> listeners = new ArrayList<FramePrincipalListener>();

	public FramePrincipal() throws TrafficDAOException {
		super("NETZ v1.0");
		add(getTabbedPanel());
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	private TabbedPanel getTabbedPanel() throws TrafficDAOException {
		if(tabbedPanel==null){
			tabbedPanel = new TabbedPanel();
			tabbedPanel.addTabbedPanelListener(new TabbedPanelListener(){

				@Override
				public void configurarEmail() {
					for(FramePrincipalListener listener:listeners){
						listener.configurarEmail();
					}
				}

				@Override
				public void configurarSms() {
					for(FramePrincipalListener listener:listeners){
						listener.configurarSms();
					}
				}

				@Override
				public void limparHistorico() {
					for(FramePrincipalListener listener:listeners){
						listener.limparHistorico();
					}
				}

				@Override
				public void editarUsuario(HostTO hostTO) {
					for(FramePrincipalListener listener:listeners){
						listener.editarUsuario(hostTO);
					}
				}

				@Override
				public void adicionarUsuario(HostTO hostTO) {
					for(FramePrincipalListener listener:listeners){
						listener.adicionarUsuario(hostTO);
					}
				}
				
			});
			tabbedPanel.addChangeListener(new ChangeListener(){

				@Override
				public void stateChanged(ChangeEvent e) {
		        	for(FramePrincipalListener listener:listeners){
						listener.salvarConfiguracoesGerais(tabbedPanel.getConfiguracoesGerais());
					}
				}
				
			});
		}
		return tabbedPanel;
	}

	public void addPacket(PacketTO packet) throws TrafficDAOException{
		getTabbedPanel().addPacket(packet);
	}
	
	
	
	public void addFramePrincipalListener(FramePrincipalListener listener){
		listeners.add(listener);
	}

	public void setConfigurations(SmsConfigurationTO smsConfigurationTO,
			EmailConfigurationTO emailConfigurationTO,
			GeneralConfigurationTO generalConfigurationTO) throws TrafficDAOException {
		getTabbedPanel().setConfigurations(smsConfigurationTO,emailConfigurationTO,generalConfigurationTO);
	}

	public void removerHistorico() {
		try {
			getTabbedPanel().removerHistorico();
		} catch (TrafficDAOException e) {
			e.printStackTrace();
		}
	}

	public void updateHostList(HostTO hostTO) throws TrafficDAOException {
		getTabbedPanel().updateHostList(hostTO);
	}

	public void addTcpViewer(ActiveConnectionsTO activeConnectionsTO) throws TrafficDAOException {
		getTabbedPanel().addTcpViewer(activeConnectionsTO);
		
	}

	public void removeTcpViewer(ActiveConnectionsTO activeConnectionsTO) throws TrafficDAOException {
		getTabbedPanel().removeTcpViewer(activeConnectionsTO);
	}
	
}
