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

package br.netz.traffic.controller;

import java.util.ArrayList;
import java.util.HashMap;

import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.traffic.listener.TCPViewerListener;
import br.netz.traffic.model.ActiveConnectionsTO;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class TCPViewer implements Runnable {

	private ArrayList<TCPViewerListener> listeners = new ArrayList<TCPViewerListener>();
	private ArrayList<PacketTO> tcpPackets = new ArrayList<PacketTO>();
	private HashMap<String, ActiveConnectionsTO> activeConnections = new HashMap<String, ActiveConnectionsTO>();
	private ActiveConnectionsTO tempActiveConnectionsTO;
	private int index = 0;
	private TrafficDAO trafficDAO;
	private ConfigurationDAO configurationDAO;

	public TCPViewer() throws TrafficDAOException, ConfigurationDAOException {
		trafficDAO = new TrafficDAO();
		configurationDAO = new ConfigurationDAO();
	}

	@Override
	public void run() {

		while (true) {
			try {
				GeneralConfigurationTO configurationTO;
				configurationTO = configurationDAO.getGeneralConfiguration();
				index = configurationTO.getLastTCPViewerIndex();

				tcpPackets = trafficDAO.getPacketsFromProtocol(index, "TCP");

				for (PacketTO packet : tcpPackets) {
					if (packet.getSyn() && packet.getAckFlag()) {
						if (!activeConnections.containsKey(packet
								.getSourceName()
								+ "," + packet.getDestinationName())) {
							tempActiveConnectionsTO = new ActiveConnectionsTO();
							tempActiveConnectionsTO.setSrc(packet
									.getSourceName());
							tempActiveConnectionsTO.setDst(packet
									.getDestinationName());
							tempActiveConnectionsTO.setStatus("Conex�o estabelecida");
							tempActiveConnectionsTO.setSrcPort(packet.getSourcePort().toString());
							tempActiveConnectionsTO.setDstPort(packet.getDestinationPort().toString());
							activeConnections.put(packet.getSourceName() + ","
									+ packet.getDestinationName(),
									tempActiveConnectionsTO);
							for (TCPViewerListener listener : listeners) {
								listener.addTcpViewer(tempActiveConnectionsTO);
							}
						}
					} else if (packet.getFin() && packet.getAckFlag()) {
						if (activeConnections.containsKey(packet
								.getSourceName()
								+ "," + packet.getDestinationName())) {
							activeConnections.remove(packet.getSourceName()
									+ "," + packet.getDestinationName());
							for (TCPViewerListener listener : listeners) {
								listener
										.removeTcpViewer(tempActiveConnectionsTO);
							}
						}
					}
					if (tcpPackets.size() > 0) {
						PacketTO pack = tcpPackets.get(tcpPackets.size() - 1);
						if (pack != null) {
							index = pack.getId();
							configurationDAO.updateLastTCPViewerIndex(index);
						}
					}

				}

			} catch (TrafficDAOException e) {
				e.printStackTrace();
			} catch (ConfigurationDAOException e) {
				e.printStackTrace();
			}
			Thread.yield();
		}
	}

	public void addTcpViewerListener(TCPViewerListener listener) {
		listeners.add(listener);
	}
}
