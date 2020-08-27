package br.netz.tests.host;

import java.util.HashMap;

import junit.framework.TestCase;
import br.netz.configuration.controller.ConfigurationController;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.hosts.controller.HostTrafficAnalisysThread;
import br.netz.hosts.controller.ListHosts;
import br.netz.hosts.model.HostDAO;
import br.netz.hosts.model.HostDAOException;
import br.netz.hosts.model.HostTO;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class TestHostTrafficAnalisys extends TestCase{

	public void testHostTrafficAnalisys(){
		
		try {
			prepareDataBase();
			
			new Thread(new HostTrafficAnalisysThread()).start();
			
			Thread.sleep(5000);
			
			HashMap<String,HostTO> hosts = ListHosts.getInstance().getOnlineHosts();
			
			assertNotNull(hosts);
			assertEquals(3, hosts.size());
			
		} catch (TrafficDAOException e) {
			e.printStackTrace();
		} catch (HostDAOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ConfigurationDAOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepareDataBase(){
		try {
			ConfigurationController.setRange("192.168.0");
			
			TrafficDAO trafficDAO = new TrafficDAO();
			HostDAO hostDAO = new HostDAO();

			PacketTO packet1 = new PacketTO();
			packet1.setSourceIp("192.168.0.2");
			packet1.setDestinationIp("192.168.0.255");
			packet1.setSourceMac("00:00:00:00:d0");
			packet1.setPacketLength(10f);
			trafficDAO.savePacket(packet1);
			
			HostTO host1 = new HostTO();
			host1.setMacAddress("00:00:00:00:d0");
			host1.setIpAddress("192.168.0.2");
			host1.setKnown(false);
			hostDAO.saveHost(host1);
			
			PacketTO packet2 = new PacketTO();
			packet2.setSourceIp("192.168.0.3");
			packet2.setDestinationIp("192.168.0.255");
			packet2.setSourceMac("00:00:00:00:f0");
			packet2.setPacketLength(15f);
			trafficDAO.savePacket(packet2);
			
			HostTO host2 = new HostTO();
			host2.setMacAddress("00:00:00:00:f0");
			host2.setIpAddress("192.168.0.3");
			host2.setKnown(true);
			hostDAO.saveHost(host2);
			
			ListHosts.getInstance().addHostOffline(host1);
			ListHosts.getInstance().addHostOffline(host2);
			
			PacketTO packet3 = new PacketTO();
			packet3.setSourceIp("192.168.0.4");
			packet3.setDestinationIp("192.168.0.255");
			packet3.setSourceMac("00:00:00:00:b0");
			packet3.setPacketLength(18f);
			trafficDAO.savePacket(packet3);
			
		} catch (TrafficDAOException e) {
			e.printStackTrace();
		} catch (HostDAOException e) {
			e.printStackTrace();
		}
	}
}
