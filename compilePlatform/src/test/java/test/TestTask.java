package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pdp.compileplatform.session.Client;
import pdp.compileplatform.session.Task;


public class TestTask {
	private Client c;
	private Task t1;
	private Task t2;
	private Task t3;
	
	@Before
	public void setUp()
	{
		c = new Client("11001");
		String src = "#include <stdio.h>"
					+"void main(void){"
					+"	printf(\"Hello world !!\n\");"
					+"}";
		t1 = new Task(c, 1, src, "" , false, null, null);
		t2 = new Task(c, 1, src, "" , true, "", "");
		t3 = new Task(c, 1, src, "" , true, null, null);
	}
	
	@Test
	public void testGetClient() {
		assertSame(t1.getClient(), c);
		assertSame(t2.getClient(), c);
		assertSame(t3.getClient(), c);
	}

	
	@Test
	public void testGetParams() {
		assertTrue(t1.getParams() != null);
		assertTrue(t2.getParams() != null);
		assertTrue(t3.getParams() != null);
	}

	@Test
	public void testGetResults() {
		assertTrue(t1.getResults() != null);
		assertTrue(t2.getResults() != null);
		assertTrue(t3.getResults() != null);
	}

	@Test
	public void testInactiveSince() {
		long time1 = t1.inactiveSince();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long time2 = t1.inactiveSince();
		assertTrue(time1 < time2);
		t1.inActivity();
		long time3 = t1.inactiveSince();
		assertTrue(time2 > time3);
	}

	@Test
	public void testInActivity() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long time1 = t1.inactiveSince();
		t1.inActivity();
		long time2 = t1.inactiveSince();
		
		assertTrue(time1 > time2);
	}

}
