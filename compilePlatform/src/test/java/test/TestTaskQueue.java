package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pdp.compileplatform.execution.TaskQueue;
import pdp.compileplatform.execution.TaskQueueInterface;
import pdp.compileplatform.session.ClientInterface;
import pdp.compileplatform.session.Task;
import pdp.compileplatform.session.TaskInterface;
import pdp.compileplatform.session.Client;

public class TestTaskQueue {

	private TaskQueueInterface ts;
	private ClientInterface c;
	private TaskInterface job;
	private TaskInterface job2;

	@Before
	public void setUp() {
		ts = new TaskQueue();
		c = new Client("11");
		job = new Task(c, 111, null, null, false, null, null);
		job2 = new Task(c, 0, null, null, false, null, null);
	}

	@Test
	public void testAddTask() {

		int oldNbTasks = ts.count();
		ts.addTask(job);
		int newNbTasks = ts.count();
		assertTrue(oldNbTasks + 1 == newNbTasks);

		ts.addTask(job2);
		TaskInterface task = null;
		for (int i = 0; i < newNbTasks+1; i++)
			task = ts.getTask();
		assertSame(task, job2);
		assertEquals(ts.count(), 0);

	}

	@SuppressWarnings("null")
	@Test(expected = Exception.class)
	public void testAddTaskV1() {
		TaskQueueInterface ts = null;
		ts.addTask(job);
	}

	@Test
	public void testGetTask() {
		
		ts.addTask(job);
		int oldNbTasks = ts.count();
		assertSame(ts.getTask(), job);
		int newNbTasks = ts.count();
		assertTrue(oldNbTasks == newNbTasks+1);
		
		while(!ts.isEmpty())
			ts.getTask();
		ts.getTask();
	}

	@SuppressWarnings("null")
	@Test(expected = Exception.class)
	public void testGetTaskV1() {
		TaskQueueInterface ts = null;
		ts.getTask();
	}

	@Test
	public void testRemoveTask() {
		ts.addTask(job);
		ts.addTask(job2);
		
		int oldNbTasks = ts.count();
		ts.removeTask(job);
		int newNbTasks = ts.count();
		
		assertTrue(oldNbTasks - 1 == newNbTasks);
		ts.removeTask(job);
		
		newNbTasks = ts.count();
		assertTrue(oldNbTasks - 1 == newNbTasks);
	}

	@Test
	public void testIsEmpty() {
		assertTrue(ts.isEmpty() == true);
		ts.addTask(job);
		assertFalse(ts.isEmpty() == true);

	}

	@Test
	public void testCount() {
		ts.addTask(job);
		assertEquals(ts.count(), 1);
		for (int i = 0; i < 2; i++)
			ts.addTask(job);
		assertTrue(ts.count() == 3);
		ts.removeTask(job);
		assertTrue(ts.count() == 2);
	}

}