/*
	NETZ - Network management support system
    Copyright (C) 2011  Alana de Almeida Brandão (alana.brandao@yahoo.com.br)
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

import java.io.IOException;

import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.GeneralConfigurationTO;

//import jpcap.JpcapCaptor;
//import jpcap.NetworkInterface;
//import jpcap.packet.Packet;
import netz.traffic.logger.NetzLogger;

public class TrafficSniffer implements Runnable{

	@Override
	public void run() {
		try {
			NetzLogger.getInstance().info("TrafficSniffer iniciado");

			String[] devices = {"dev1", "dev2"}; //JpcapCaptor.getDeviceList();
			ConfigurationDAO configurationDAO = new ConfigurationDAO();
			GeneralConfigurationTO configurationTO = new GeneralConfigurationTO();
			configurationTO = configurationDAO.getGeneralConfiguration();
			String packet;
			String net = null;
			String a;
			String b;

			if(configurationTO.getNetworkInterface()!=null){
				for(int i=0;i<devices.length; i++){
					a = configurationTO.getNetworkInterface().trim();
					b =  devices[i];
					if(a.equals(b)){
						net = devices[i];
					}
				}
			}

			if(net==null){
				net=devices[0];
			}
			String captor = ""; //JpcapCaptor.openDevice(net, 65535, true, 20);

			while (true) {
				packet = "package"; //captor.getPacket();
				if(packet!=null) {
					TrafficController.getInstance().addPacket(packet);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao tentar iniciar o Netz!");

		}

	}
}