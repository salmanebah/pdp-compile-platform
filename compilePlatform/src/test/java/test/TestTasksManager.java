package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import pdp.compileplatform.session.TasksManager;
import pdp.compileplatform.session.Client;
import pdp.compileplatform.session.EnumStatus;
import pdp.compileplatform.session.Task;
import pdp.compileplatform.session.TasksManagerInterface;


public class TestTasksManager {
    private Client c;
    
    private Task t1;
    private Task t2;
    private Task t3;
    
    private TasksManagerInterface ctm;
    
    @Before
    public void setUp() throws Exception {
   	 c = new Client("11001");
   	 
   	 t1 = new Task(c, 0, "src1", "", false, "", "");
   	 t2 = new Task(c, 0, "src2", "", true, "", "");
   	 t3 = new Task(c, 1, "src3", "", true, "", "");
    
   	 ctm = new TasksManager();
    }
    
    @Test
    public void testAddTask(){
   	 ctm.clearAllTasks();
   	 ctm.addTask(t1);
   	 assertTrue(ctm.getTaskId(t1) != -1);
	}
    
    @Test
    public void testGetTaskId() {
   	 ctm.clearAllTasks();
   	 ctm.addTask(t1);
   	 assertTrue(ctm.getTaskId(t1) == 0);
    }
    @Test
	public void testGetTaskById() {
   	 ctm.clearAllTasks();
   	 ctm.addTask(t1);
   	 assertTrue(ctm.getTaskById(0) == t1);
    }    
    
	@Test
	public void testClearAllTasks() {
   	 ctm.addTask(t1);
   	 ctm.addTask(t2);
   	 ctm.addTask(t3);
   	 
   	 t1.getResults().setStatus(EnumStatus.DONE);
   	 t2.getResults().setStatus(EnumStatus.DONE);
   	 t3.getResults().setStatus(EnumStatus.DONE);
   	 
   	 ctm.clearAllTasks();
   	 
   	 assertFalse(new File(t1.getPath()).exists());
   	 assertFalse(new File(t2.getPath()).exists());
   	 assertFalse(new File(t3.getPath()).exists());
   	 
   	 assertTrue(ctm.getTaskId(t1) == -1);
   	 assertTrue(ctm.getTaskId(t2) == -1);
   	 assertTrue(ctm.getTaskId(t3) == -1);
	}
}
