package br.netz.tests.traffic;

import java.util.ArrayList;

import junit.framework.TestCase;
import br.netz.traffic.controller.TrafficSniffer;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class TestTraffic extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testTraffic() throws TrafficDAOException{
		
		new Thread(new TrafficSniffer()).start();
		 
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TrafficDAO trafficDAO = new TrafficDAO();
		ArrayList<PacketTO> packets = new ArrayList<PacketTO>();
		packets = trafficDAO.getPackets();
		
		assertNotNull(packets);
	}
	
	public void testBroadcast(){
		try {
			TrafficDAO trafficDAO = new TrafficDAO();
			
			PacketTO packet1 = new PacketTO();
			packet1.setSourceIp("192.168.0.2");
			packet1.setDestinationIp("192.168.0.255");
			packet1.setSourceMac("00:00:00:00:d0");
			packet1.setPacketLength(10f);
			trafficDAO.savePacket(packet1);
			
			PacketTO packet2 = new PacketTO();
			packet2.setSourceIp("192.168.0.3");
			packet2.setDestinationIp("192.168.0.255");
			packet2.setSourceMac("00:00:00:00:f0");
			packet2.setPacketLength(15f);
			trafficDAO.savePacket(packet2);
			
			ArrayList<PacketTO> packets = trafficDAO.getPacketsBroadcastFromIndex(0, "192.168.0.255");
			
			assertNotNull(packets);
			assertTrue(packets.size()>2);
		} catch (TrafficDAOException e) {
			e.printStackTrace();
		}
	}
	
}
