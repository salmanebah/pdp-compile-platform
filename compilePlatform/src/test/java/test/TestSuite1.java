package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestClient.class, TestClientsManager.class,
		TestTasksManager.class, TestTask.class, TestTaskQueue.class })
public class TestSuite1 {
	
}
