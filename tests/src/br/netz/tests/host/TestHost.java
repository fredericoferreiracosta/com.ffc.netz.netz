package br.netz.tests.host;

import br.netz.hosts.model.HostDAO;
import br.netz.hosts.model.HostDAOException;
import br.netz.hosts.model.HostTO;
import junit.framework.TestCase;

public class TestHost extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testHost() throws HostDAOException {
		HostDAO hostDAO = new HostDAO();
		
		//Create a host
		HostTO hostSaved = new HostTO();
		hostSaved.setHostName("Fredy-Note");
		hostSaved.setIpAddress("192.168.0.100");
		hostSaved.setKnown(true);
		int i = (int) (1+Math.random()*10);  
		int j = (int) (1+Math.random()*10);  
		hostSaved.setMacAddress(i + ":" + j + ":00:00:f0");
		hostSaved.setViewName("Fredy");
		
		hostDAO.saveHost(hostSaved);
		
		//Retrieve the created host
		HostTO hostReturned = hostDAO.getHost(hostSaved.getMacAddress());
		assertEquals(hostSaved.getHostName(), hostReturned.getHostName());
		assertEquals(hostSaved.getIpAddress(), hostReturned.getIpAddress());
		assertEquals(hostSaved.getMacAddress(), hostReturned.getMacAddress());
		assertEquals(hostSaved.getViewName(), hostReturned.getViewName());
		
		//Update the host data
		hostSaved.setHostName("Alana-Note");
		hostSaved.setViewName("Alana");
		
		hostDAO.updateHost(hostSaved);
		
		//Retrieve the updated host
		hostReturned = hostDAO.getHost(hostSaved.getMacAddress());
		assertEquals(hostSaved.getHostName(), hostReturned.getHostName());
		assertEquals(hostSaved.getViewName(), hostReturned.getViewName());
		
		//Verify the host situation (known)
		int hostExistsReturned = hostDAO.hostExists(hostSaved.getMacAddress());
		assertEquals(hostExistsReturned, HostDAO.HOST_KNOWN);
		
		hostSaved.setKnown(false);
		hostDAO.updateHost(hostSaved);

		//Verify the host situation (unknown)
		hostExistsReturned = hostDAO.hostExists(hostSaved.getMacAddress());
		assertEquals(hostExistsReturned, HostDAO.HOST_UNKNOWN);
		
		//Verify the host situation (non existent)
		hostExistsReturned = hostDAO.hostExists("00:00:00:00:d0");
		assertEquals(hostExistsReturned, HostDAO.HOST_NONEXISTENT);
		
		hostDAO.deleteAllHosts();
	}
	
}
