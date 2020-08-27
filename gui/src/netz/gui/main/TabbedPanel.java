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

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import br.netz.hosts.model.HostTO;
import br.netz.traffic.model.ActiveConnectionsTO;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAOException;

import netz.gui.configuracoes.PanelConfiguracoes;
import netz.gui.listeners.PanelConfiguracoesListener;
import netz.gui.listeners.PanelUnificadaListener;
import netz.gui.listeners.TabbedPanelListener;
import netz.gui.monitor.PanelMonitor;
import netz.gui.unificada.PanelUnificada;

public class TabbedPanel extends JTabbedPane {

	private static final long serialVersionUID = 7876272562056355907L;

	private PanelUnificada panelGeral;
	private PanelConfiguracoes panelConfiguracoes;
	private PanelMonitor panelMonitor;
	
	private ArrayList<TabbedPanelListener> listeners = new ArrayList<TabbedPanelListener>();	
	
	public TabbedPanel() throws TrafficDAOException {
		super();
		initialize();
		setSize(new Dimension(1000,680));
	}
	
	public void initialize() throws TrafficDAOException{
		addTab("Unificada", getPanelGeral());
		addTab("Monitor", getPanelMonitor());
		addTab("Configura��es", getPanelConfiguracoes());
	}
	
	private PanelUnificada getPanelGeral() throws TrafficDAOException {
		if(panelGeral==null){
			panelGeral = new PanelUnificada();
			panelGeral.addPanelUnificadaListener(new PanelUnificadaListener(){

				@Override
				public void editarUsuario(HostTO hostTO) {
					for(TabbedPanelListener listener: listeners){
						listener.editarUsuario(hostTO);
					}
				}

				@Override
				public void adicionarUsuario(HostTO hostTO) {
					for(TabbedPanelListener listener: listeners){
						listener.adicionarUsuario(hostTO);
					}
				}
				
			});
		}
		return panelGeral;
	}
	private PanelConfiguracoes getPanelConfiguracoes() {
		if(panelConfiguracoes==null){
			panelConfiguracoes = new PanelConfiguracoes();
			panelConfiguracoes.addPanelConfiguracoesListener(new PanelConfiguracoesListener() {
			
				@Override
				public void configurarEmail() {
					for(TabbedPanelListener listener:listeners){
						listener.configurarEmail();
					}
				}

				@Override
				public void configurarSms() {
					for(TabbedPanelListener listener:listeners){
						listener.configurarSms();
					}
				}

				@Override
				public void limparHistorico() {
					for(TabbedPanelListener listener:listeners){
						listener.limparHistorico();
					}
				}
			});
		}
		return panelConfiguracoes;
	}

	private PanelMonitor getPanelMonitor() {
		if(panelMonitor==null){
			panelMonitor = new PanelMonitor();
		}
		return panelMonitor;
	}
	
	public void addPacket(PacketTO packet) throws TrafficDAOException{
		getPanelMonitor().addPacket(packet);
	}

	public void addTabbedPanelListener(TabbedPanelListener listener){
		listeners.add(listener);
	}

	public void setConfigurations(SmsConfigurationTO smsConfigurationTO,
			EmailConfigurationTO emailConfigurationTO,
			GeneralConfigurationTO generalConfigurationTO) {
		getPanelConfiguracoes().setConfigurations(smsConfigurationTO,emailConfigurationTO,generalConfigurationTO);
	}

	public GeneralConfigurationTO getConfiguracoesGerais() {
		return getPanelConfiguracoes().getConfiguracoesGerais();
	}

	public void removerHistorico() {
		getPanelMonitor().limparMonitor();
		//getPanelGeral().limparHistorico();
	}

	public void updateHostList(HostTO hostTO) throws TrafficDAOException {
		getPanelGeral().updateHostList(hostTO);
	}

	public void addTcpViewer(ActiveConnectionsTO activeConnectionsTO) throws TrafficDAOException {
		getPanelGeral().addTcpViewer(activeConnectionsTO);
	}

	public void removeTcpViewer(ActiveConnectionsTO activeConnectionsTO) throws TrafficDAOException {
		getPanelGeral().removeTcpViewer(activeConnectionsTO);
	}
	
}
