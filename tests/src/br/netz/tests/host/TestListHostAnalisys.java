package br.netz.tests.host;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import junit.framework.TestCase;
import br.netz.hosts.controller.ListHosts;
import br.netz.hosts.controller.ListHostsAnalisysThread;
import br.netz.hosts.model.HostDAO;
import br.netz.hosts.model.HostDAOException;
import br.netz.hosts.model.HostTO;

public class TestListHostAnalisys extends TestCase{

	public void testHostsList(){
		try {

			prepareHostList();
			new Thread(new ListHostsAnalisysThread()).start();
			
			Thread.sleep(5000);
			
			HashMap<String,HostTO> hostsOnline = ListHosts.getInstance().getOnlineHosts();
			HashMap<String,HostTO> hostsOffline = ListHosts.getInstance().getOfflineHosts();
			
			assertTrue(hostsOnline.size()==2);
			assertTrue(hostsOffline.size()==1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void prepareHostList(){
		
		try {
			HostDAO hostDAO = new HostDAO();

			HostTO host1 = new HostTO();
			host1.setMacAddress("00:00:00:00:d0");
			host1.setIpAddress(InetAddress.getLocalHost().getHostAddress());
			host1.setKnown(false);
			hostDAO.saveHost(host1);
			
			HostTO host2 = new HostTO();
			host2.setMacAddress("00:00:00:00:f0");
			host2.setIpAddress("192.168.1.103");//This host exists
			host2.setKnown(true);
			hostDAO.saveHost(host2);
			
			HostTO host3 = new HostTO();
			host3.setMacAddress("00:00:00:00:b0");
			host3.setIpAddress("192.168.1.3");//This doesn't host exists
			host3.setKnown(true);
			hostDAO.saveHost(host3);
			
			ListHosts.getInstance().addHostOnline(host1);
			ListHosts.getInstance().addHostOnline(host2);
			ListHosts.getInstance().addHostOnline(host3);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (HostDAOException e) {
			e.printStackTrace();
		}
	}
}
