package pdp.compileplatform.session;


import java.util.List;
import java.util.ArrayList;

import pdp.compileplatform.frontend.RequestHandler;

/**
 * A concrete implementation of the <tt>TaskManagerInterface</tt> interface 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TasksManagerInterface
 * @see TaskInterface
 * @see Task
 * 
 *
 */
public class TasksManager implements TasksManagerInterface{
    
	protected List<TaskInterface> lstTask;

    
	/** 
	 * Construct a TaskManager
	 * @param tasksCleanerTimerInMillis the time in milliseconds for the frequence 
	 * of cleaning of the TaskManager's tasks
	 * @param maxTaskInactivityDurationInMillis the time in milliseconds from which
	 * a task is considered as too old
	 */
	 
    public TasksManager(){
   	 	this.lstTask = new ArrayList<TaskInterface>(); 
    }

    /**
     * add a task to the TaskManager
     * @param task the task to add to the the TaskInterface=
     */
    @Override
    public void addTask(TaskInterface task){
    	this.lstTask.add(task);
    }
    
    /**
     * Return the id of the task given as parameter
     * @param task the task of which the id will be given
     * @return the id of the task given as parameter
     */
    @Override
	public int getTaskId(TaskInterface task) {
		return this.lstTask.indexOf(task);
	}
    
    /**
     * Return the task corresponding to the id given
     * in parameter
     * @param taskId the id of which the task will be given
     * @return the task corresponding to the id given
     * as parameter 
     */
    @Override
    public TaskInterface getTaskById(int taskId) {
    	if (taskId < 0 || taskId >= lstTask.size()){
    		return null;
    	}
    	return lstTask.get(taskId);
    }
    
    /**
     * Remove a task from the TaskManager
     * @param task the TaskInterface to remove from the 
     * TaskManager
     */
    @Override
	public void removeTask(TaskInterface task) {
    	task.kill();
		this.lstTask.set(getTaskId(task), null);
    }

    /**
     *Remove all the tasks from the TaskManager 
     */
    @Override
    public void clearAllTasks() {
    	for(TaskInterface task : lstTask) 
    		if (task != null)    		
				task.kill();
    	
    	lstTask.clear();
    }
	
    /**
	 * Destroy all the old tasks from its list of tasks
	 */
	public void cleanTasks(long maxTaskInactivityDuration) {
		for (TaskInterface task : lstTask) {
			if(task != null){
				if (((task.getResults().getStatus() == EnumStatus.DONE) 
					 || (task.getResults().getStatus() == EnumStatus.ERROR))
					&& (task.inactiveSince() > maxTaskInactivityDuration)) {
					RequestHandler.printLog("task deleted : " + task.getPath());
					task.getClient().removeTask(task);
				}
			}
		}
	}
}