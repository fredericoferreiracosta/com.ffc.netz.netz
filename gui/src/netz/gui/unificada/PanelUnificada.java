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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import netz.gui.listeners.PanelUnificadaListener;
import netz.gui.listeners.PanelUsuariosListener;
import br.netz.graphic.view.GraphicPanel;
import br.netz.hosts.model.HostTO;
import br.netz.traffic.model.ActiveConnectionsTO;
import br.netz.traffic.model.TrafficDAOException;

public class PanelUnificada extends JPanel {

	private static final long serialVersionUID = 207841550359582116L;

	private PanelUsuarios panelUsuarios;
	private GraphicPanel panelGrafico;
	private PanelTCPViewer panelTCPViewer;
	private ArrayList<PanelUnificadaListener> listeners = new ArrayList<PanelUnificadaListener>();

	public PanelUnificada() throws TrafficDAOException {
		super();
		initialize();
		setPreferredSize(new Dimension(980,680));
	}
	
	public void initialize() throws TrafficDAOException{
		setLayout(new BorderLayout());
		add(getPanelUsuarios(),BorderLayout.WEST);
		add(getPanelGrafico(),BorderLayout.EAST);
		add(getPanelTCPViewer(),BorderLayout.SOUTH);
	}

	private PanelUsuarios getPanelUsuarios() {
		if(panelUsuarios==null){
			panelUsuarios = new PanelUsuarios();
			panelUsuarios.addPanelUsuariosListener(new PanelUsuariosListener(){

				@Override
				public void editarUsuario(HostTO hostTO) {
					for(PanelUnificadaListener listener:listeners){
						listener.editarUsuario(hostTO);
					}
				}

				@Override
				public void adicionarUsuario(HostTO hostTO) {
					for(PanelUnificadaListener listener:listeners){
						listener.adicionarUsuario(hostTO);
					}
				}
				
			});
		}
		return panelUsuarios;
	}

	private GraphicPanel getPanelGrafico() throws TrafficDAOException {
		if(panelGrafico==null){
			panelGrafico = new GraphicPanel();
		}
		return panelGrafico;
	}

	private PanelTCPViewer getPanelTCPViewer() {
		if(panelTCPViewer==null) {
			panelTCPViewer = new PanelTCPViewer();
		}
		return panelTCPViewer;
	}

	public void addPanelUnificadaListener(PanelUnificadaListener listener){
		listeners.add(listener);
	}

	public void updateHostList(HostTO hostTO) {
		getPanelUsuarios().updateHostList(hostTO);
		repaint();
	}

	public void removeTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
		getPanelTCPViewer().removeTcpViewer(activeConnectionsTO);
	}

	public void addTcpViewer(ActiveConnectionsTO activeConnectionsTO) {
		getPanelTCPViewer().addTcpViewer(activeConnectionsTO);
	}

}
