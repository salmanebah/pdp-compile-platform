package test;

import static org.junit.Assert.*; 

import org.junit.Before;
import org.junit.Test;

import pdp.compileplatform.session.ClientsManager;
import pdp.compileplatform.session.ClientInterface;
import pdp.compileplatform.session.ClientsManagerInterface;

public class TestClientsManager {
	private ClientInterface a;
	private ClientInterface b;
	private ClientsManagerInterface ccm;
	
	@Before
	public void setUp() throws Exception {		 
		 ccm = new ClientsManager(5000,3000,3000);
	}

	@Test
	public void testAddClient() {
		a = ccm.addClient();
		b = ccm.addClient();
		assertTrue(a != null && b != null);
		assertNotSame (a,b);
	}

	@Test
	public void testGetClient() {
		a = ccm.addClient();
		b = ccm.addClient();
		ClientInterface c = ccm.getClient(a.getSessionKey());
		ClientInterface d = ccm.getClient(b.getSessionKey());
		assertSame (c,a);
		assertSame (d,b);
		assertTrue (c.getSessionKey()!= d.getSessionKey());
	}
	
	@Test
	public void testCountClient() {
		a = ccm.addClient();
		b = ccm.addClient();
		int i = ccm.countClient();
	 	assertTrue(i == 2);
	}

	@Test
	public void testRemoveClient() {
		a = ccm.addClient();
		b = ccm.addClient();
		String sessionkey = a.getSessionKey();
		ccm.removeClient(a);
		assertTrue (ccm.getClient(sessionkey)== null);
		int i = ccm.countClient();
	 	assertTrue(i == 1);
	}
}
