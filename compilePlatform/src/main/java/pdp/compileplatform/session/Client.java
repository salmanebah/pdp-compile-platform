package pdp.compileplatform.session;

import java.io.File;
import java.util.Date;

/**
 * A concrete implementation of the <tt>ClientInterface</tt> interface 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see ClientInterface
 * @see ClientManagerInterface
 * @see TaskInterface
 * @see Task
 *
 */
public class Client implements ClientInterface {

	protected final String DefaultPath = File.separatorChar + "tmp";
		
	private String sessionKey;
	
	private TasksManagerInterface tasksManager;
	
	private Date sessionStartAt;
	private Date sessionLastActivity;
	
	/**
	 * Construct an object Client	
	 * @param sessionKey the string of the client's sessionKey 
	 * @param tasksCleanerTimer the time frequency in milliseconds of the
	 * cleaner timer's waking  
	 * @param maxTaskInactivityDuration the time in milliseconds from which
	 * a task is considered as too old
	 */
	public Client(String sessionKey) {
		this.sessionKey = sessionKey;
	
		this.tasksManager = new TasksManager();
		
		this.sessionStartAt = new Date(System.currentTimeMillis());
		this.sessionLastActivity = sessionStartAt;
		
		File dir = new File(this.getPath());
		dir.mkdir();
		dir.setReadable(true, false);
		dir.setWritable(true,false);
		dir.setExecutable(true, false);
	}

	/**
	 * Return the sessionKey of the client
	 * @return the String representing the sessionKey of the client
	 */
	@Override
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * Return the path to the client's directory on the server
	 * @return a string representing the path to the client's directory 
	 * on the server
	 */
	@Override
	public String getPath(){
		return DefaultPath + File.separatorChar + this.getSessionKey();
	}
	
	/**
	 * Add a task to the client's list of TaskInterface
	 * @param task the TaskInterface to add to the client's list of
	 * TaskInterface 
	 */
	@Override
	public void addTask(TaskInterface task) {
		this.tasksManager.addTask(task);
		this.inActivity();
	}
	
	/**
	 * Remove a task from the client's list of TaskInterface
	 * @param task the TaskInterface to remove from the cllient's list 
	 * of TaskInterface
	 */
	@Override
	public void removeTask(TaskInterface task) {
		 tasksManager.removeTask(task);
	}
	
	/**
	 * Return the task's id given in parameters
	 * @return the TaskInterface related to the id
	 */
	@Override
	public TaskInterface getTaskById(int idTask) {
		return tasksManager.getTaskById(idTask);
	}

	/**
	 * Return the id of the TaskInterface given in parameters
	 * @return the id of the TaskInterface given in parameters
	 */
	@Override
	public int getTaskId(TaskInterface task) {
		return tasksManager.getTaskId(task);
	}
	
	/**
	 * Return the duration since the client connected
	 * @return a long corresponding to the duration since the 
	 * client connected
	 */
	@Override
	public long connectedSince() {
		return timeElapsedSince(this.sessionStartAt);
	}

	/**
	 * Return the duration since the client sent his last request
	 * @return a long corresponding to the duration since the 
	 * client sent his last request
	 */
	@Override
	public long inactiveSince() {
		return timeElapsedSince(this.sessionLastActivity);
	}

	/**
	 * Actualize the date of the client's last action
	 */
	@Override
	public void inActivity() {
		this.sessionLastActivity = new Date(System.currentTimeMillis());		
	}
	
	/**
	 * Destroy all the old tasks from the client's list of tasks
	 */
	public void cleanTasks(long maxTaskInactivityDuration){
		this.tasksManager.cleanTasks(maxTaskInactivityDuration);
	}
	
	/**
	 * Destroy all the tasks from the client's list of tasks and the 
	 * directory related to the client
	 */
	@Override
	public void kill() {
		tasksManager.clearAllTasks();
		File dir = new File(this.getPath());
		dir.delete();
	}
	
	private static long timeElapsedSince(Date start) {
		long since = start.getTime();
		long now = System.currentTimeMillis();
		return (now - since);
	}
	
}
