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

package br.netz.hosts.controller;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.HashMap;

import br.netz.hosts.listener.HostListener;
import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.hosts.model.HostTO;
import br.netz.notification.controller.NetzSystemTray;


public class ListHosts {
	
	private static HashMap<String,HostTO> onlineHosts; 
	private static HashMap<String,HostTO> offlineHosts;
	private static ListHosts instance;
	ConfigurationDAO configurationDAO;
	private GeneralConfigurationTO configurationTO;
	
	private ArrayList<HostListener> listeners = new ArrayList<HostListener>();
	
	private ListHosts() {
		try {
			configurationDAO = new ConfigurationDAO();
			configurationTO = configurationDAO.getGeneralConfiguration();
		} catch (ConfigurationDAOException e) {
			e.printStackTrace();
		}
	}
	
	public static ListHosts getInstance() {
		if(instance==null) {
			instance = new ListHosts();
			onlineHosts = new HashMap<String,HostTO>();
			offlineHosts = new HashMap<String,HostTO>();
		}
		return instance;
	}
	
	public HashMap<String,HostTO> getOnlineHosts() {
		return onlineHosts;
	}
	
	public HashMap<String,HostTO> getOfflineHosts() {
		return offlineHosts;
	}
	
	public void addHostOnline(HostTO hostTO) {
		synchronized (onlineHosts) {
			if(!onlineHosts.containsKey(hostTO.getMacAddress())){
				onlineHosts.put(hostTO.getMacAddress(),hostTO);
				try {
					configurationTO = configurationDAO.getGeneralConfiguration();
				} catch (ConfigurationDAOException e) {
					e.printStackTrace();
				}
				if(configurationTO.isNotifyHostEntry()) {
					NetzSystemTray.displayMessage("O dispositivo " + hostTO.getHostName() + " acabou de entrar.", MessageType.INFO);
				}
				for(HostListener listener : listeners){
					listener.addOnlineHost(hostTO);
				}
			}
		}
		
	}
	
	public void addHostOffline(HostTO hostTO) {
		synchronized (offlineHosts) {
			if(!offlineHosts.containsKey(hostTO.getMacAddress())){
				offlineHosts.put(hostTO.getMacAddress(),hostTO);
				for(HostListener listener : listeners){
					listener.addOfflineHost(hostTO);
				}
			}
		}
		
	}
	
	public void removeOnlineHost(HostTO hostTO) {
		synchronized (onlineHosts) {
			if(onlineHosts.containsKey(hostTO.getMacAddress())){
				onlineHosts.remove(hostTO.getMacAddress());
				for(HostListener listener : listeners){
					listener.removeOnlineHost(hostTO);
				}
			}
		}
		
	}
	
	public void removeOfflineHost(HostTO hostTO){
		synchronized (offlineHosts) {
			if(offlineHosts.containsKey(hostTO.getMacAddress())){
				offlineHosts.remove(hostTO.getMacAddress());
				for(HostListener listener : listeners){
					listener.removeOfflineHost(hostTO);
				}
			}
		}
		
	}
	
	public void addHostListener(HostListener listener){
		listeners.add(listener);
	}
	
}
