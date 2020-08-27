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

import br.netz.traffic.listener.PacketListener;
import br.netz.traffic.listener.TrafficControllerListener;
import br.netz.traffic.model.PacketTO;

public class TrafficController {

	private static ArrayList<String> packets = new ArrayList<String>();
	private static TrafficController instance;
	private static ArrayList<TrafficControllerListener> listeners = new ArrayList<TrafficControllerListener>();
	
	private static TrafficSniffer trafficSniffer;
	private static TrafficStorage trafficStorage;
	
	private TrafficController() {
		
	}
	
	public static void initSniffer(){
		new Thread(getTrafficSniffer()).start();
		new Thread(getTrafficStorage()).start();
	}
	
	public static TrafficController getInstance(){
		if(instance==null){
			instance = new TrafficController();
		}
		return instance;
	}
	
	public ArrayList<String> getPackets() {
		synchronized (packets) {
			return packets;
		}	
	}
	
	public void addPacket(String p){
		synchronized (packets) {
			packets.add(p);
		}
	}
	
	public void clearPackets() {
		synchronized (packets) {
			packets.clear();
		}
	}

	private static TrafficSniffer getTrafficSniffer() {
		if(trafficSniffer==null){
			trafficSniffer = new TrafficSniffer();
		}
		return trafficSniffer;
	}

	private static TrafficStorage getTrafficStorage() {
		if(trafficStorage==null){
			trafficStorage = new TrafficStorage();
			trafficStorage.addPacketListener(new PacketListener(){

				@Override
				public void newPacket(PacketTO packet) {
					for(TrafficControllerListener listener : listeners){
						listener.newPacket(packet);
					}
				}
				
			});
		}
		return trafficStorage;
	}
	
	public void addTrafficControllerListener(TrafficControllerListener listener){
		listeners.add(listener);
	}
	
}
