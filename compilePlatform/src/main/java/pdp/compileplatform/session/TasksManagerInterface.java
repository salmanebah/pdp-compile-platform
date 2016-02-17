package pdp.compileplatform.session;

/**
 * This interface presents the different methods that must be implemented for a TasksManager. 
 * A TasksManager must allow to add and remove tasks and to set the amount of time from which
 * a task task is considered as too old and will be deleted at the next cleaning. The time frequency 
 * of cleaning must be modifiable;
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TasksManager
 * @see TaskInterface
 * @see Task
 */
public interface TasksManagerInterface{
	
	/**
     * add a task to the TaskManager
     * @param task the task to add to the the TaskInterface=
     */
	public void addTask(TaskInterface task);
	
    /**
     * Remove a task from the TaskManager
     * @param task the TaskInterface to remove from the 
     * TaskManager
     */
	public void removeTask(TaskInterface task);
	
    /**
     * Return the id of the task given as parameter
     * @param task the task of which the id will be given
     * @return the id of the task given as parameter
     */
	public int getTaskId(TaskInterface task);
	
    /**
     * Return the task corresponding to the id given
     * in parameter
     * @param taskId the id of which the task will be given
     * @return the task corresponding to the id given
     * as parameter 
     */
	public TaskInterface getTaskById(int taskId);
	
    /**
     *Remove all the tasks from the TaskManager 
     */
	public void clearAllTasks();
	
	/**
	 * Destroy all the old tasks from its list of tasks
	 */
	public void cleanTasks(long maxTaskInactivityDuration);
}

