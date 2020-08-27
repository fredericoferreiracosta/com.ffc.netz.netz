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

package netz.gui.controller;

import javax.swing.JOptionPane;

import netz.gui.listeners.FrameUsuarioListener;
import netz.gui.main.FramePrincipal;
import netz.gui.unificada.FrameUsuario;
import netz.traffic.logger.NetzLogger;

import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.hosts.model.HostDAO;
import br.netz.hosts.model.HostDAOException;
import br.netz.hosts.model.HostTO;
import br.netz.traffic.controller.TCPViewer;
import br.netz.traffic.listener.TCPViewerListener;
import br.netz.traffic.model.ActiveConnectionsTO;
import br.netz.traffic.model.TrafficDAOException;

public class UnificadaController {
	
	private HostDAO hostDAO;
	private FrameUsuario frameUsuario;
	private FramePrincipal framePrincipal;
	private TCPViewer tcpViewer;
	
	public UnificadaController(HostDAO hostDAO, FramePrincipal framePrincipal) throws TrafficDAOException, ConfigurationDAOException {
		this.hostDAO = hostDAO;
		this.framePrincipal = framePrincipal;
		new Thread(getTcpViewer()).start();
	}

	public void salvarHost(HostTO hostTO){
		try {
			hostTO.setKnown(true);
			hostDAO.updateHost(hostTO);
			JOptionPane
			.showMessageDialog(
					null,
					"Informa��es do host salvas com sucesso! ",
					"Informa��es do host",
					JOptionPane.INFORMATION_MESSAGE);
			getFrameUsuario().dispose();
			framePrincipal.updateHostList(hostTO);

		} catch (HostDAOException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao salvar informa��es do host! ",
					"Informa��es do host",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"HostDAOException: " + e.getMessage());
		} catch (TrafficDAOException e) {
			e.printStackTrace();
		}
	}
	
	public void editarUsuario(HostTO hostTO) {
		getFrameUsuario().setDados(hostTO);
		getFrameUsuario().setVisible(true);
	}
	
	private FrameUsuario getFrameUsuario() {
		
		if(frameUsuario==null){
			frameUsuario = new FrameUsuario();
			frameUsuario.addFrameUsuarioListener(new FrameUsuarioListener(){

				@Override
				public void salvarHost(HostTO hostTO) {
					UnificadaController.this.salvarHost(hostTO);
				}
			});
		}
		return frameUsuario;
	}

	private TCPViewer getTcpViewer() throws TrafficDAOException, ConfigurationDAOException {
		if(tcpViewer == null) {
			tcpViewer = new TCPViewer();
			tcpViewer.addTcpViewerListener(new TCPViewerListener() {

				@Override
				public void addTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
					try {
						framePrincipal.addTcpViewer(activeConnectionsTO);
					} catch (TrafficDAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}

				@Override
				public void removeTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
						try {
							framePrincipal.removeTcpViewer(activeConnectionsTO);
						} catch (TrafficDAOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
			});
		}
		
		return tcpViewer;
	}

	public void salvarUsuario(HostTO hostTO) {
		UnificadaController.this.salvarHost(hostTO);
	}
}
