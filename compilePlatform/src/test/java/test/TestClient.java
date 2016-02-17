package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import pdp.compileplatform.session.Client;
import pdp.compileplatform.session.ClientInterface;
import pdp.compileplatform.session.EnumStatus;
import pdp.compileplatform.session.Task;
import pdp.compileplatform.session.TaskInterface;

public class TestClient {
	private Client c;
	private Client d;

	@Before
	public void setUp() {
		c = new Client("11001");
	}

	@Test
	public void testGetSessionKey() {
		assertTrue(c.getSessionKey() == "11001");
	}

	@Test(expected = NullPointerException.class)
	public void testGetSessionKeyV1() {
		d.getSessionKey();
	}

	@Test
	public void testAddTask() {
		TaskInterface task = new Task(c, 0, "test", "test", false, "test",
				"test");
		assertSame(c, task.getClient());
	}

	@Test
	public void testRemoveTask() {
		TaskInterface task = new Task(c, 0, "test", "test", false, "test",
				"test");
		int oldTaskId = c.getTaskId(task);
		c.removeTask(task);

		assertSame(null, c.getTaskById(oldTaskId));
	}

	@Test
	public void testGetTaskById() {
		TaskInterface task = new Task(c, 0, "test", "test", false, "test",
				"test");
		int id = c.getTaskId(task);
		assertEquals(task, c.getTaskById(id));
	}

	@Test
	public void testConnectedSince() {
		long t1 = c.connectedSince();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long t2 = c.connectedSince();
		assertTrue(t2 > t1);
	}

	@Test
	public void testInactiveSince() {
		long t1 = c.inactiveSince();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long t2 = c.inactiveSince();
		assertTrue(t1 < t2);
		TaskInterface task = new Task(c, 1, "print", null, false, null, null);
		c.addTask(task);
		long t3 = c.inactiveSince();
		assertTrue(t2 > t3);

	}

	@Test
	public void testInActivity() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long t1 = c.inactiveSince();

		c.inActivity();
		long t2 = c.inactiveSince();

		assertTrue(t1 > t2);

	}

	@Test
	public void testKill() {
		ClientInterface myClient = new Client("1111111");
		TaskInterface t = new Task(myClient, 0, "test", "test", false, "test",
				"test");
		String oldDir = myClient.getPath();
		TaskInterface t1 = new Task(myClient, 0, "test", "test", false, "test",
				"test");
		TaskInterface t2 = new Task(myClient, 0, "test", "test", false, "test",
				"test");
		TaskInterface t3 = new Task(myClient, 0, "test", "test", false, "test",
				"test");
		TaskInterface t4 = new Task(myClient, 0, "test", "test", false, "test",
				"test");
		t.getResults().setStatus(EnumStatus.DONE);
		t1.getResults().setStatus(EnumStatus.DONE);
		t2.getResults().setStatus(EnumStatus.ERROR);
		t3.getResults().setStatus(EnumStatus.DONE);
		t4.getResults().setStatus(EnumStatus.ERROR);
		myClient.kill();
		File dir = new File(oldDir);

		assertFalse(dir.exists());
	}
}
