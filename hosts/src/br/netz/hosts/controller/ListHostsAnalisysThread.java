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

import java.util.ArrayList;
import java.util.HashMap;

import br.netz.hosts.model.HostTO;

public class ListHostsAnalisysThread implements Runnable {

	public ListHostsAnalisysThread() {
	}

	@Override
	public void run() {
		while (true) {
			HashMap<String, HostTO> tempOn = ListHosts.getInstance()
					.getOnlineHosts();
			ArrayList<HostTO> hostsOn = new ArrayList<HostTO>();
			hostsOn.addAll(tempOn.values());

			ArrayList<HostTO> hostsToRemove = new ArrayList<HostTO>();

			for (int i = 0; i < hostsOn.size(); i++) {
				HostTO host = hostsOn.get(i);
				boolean online = HostController.testHostOnline(host.getIpAddress());
				if (!online) {
					hostsToRemove.add(host);
				}
			}
			for (int i = 0; i < hostsToRemove.size(); i++) {
				HostTO host = (HostTO) hostsToRemove.get(i);
				ListHosts.getInstance().removeOnlineHost(host);
				ListHosts.getInstance().addHostOffline(host);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Thread.yield();
		}
	}

	
}
