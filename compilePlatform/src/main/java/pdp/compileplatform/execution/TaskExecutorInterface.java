package pdp.compileplatform.execution;

import pdp.compileplatform.frontend.Lang;
/**
 *
 * This interface presents the services that an object must 
 * implements to handle clients request for compilation, execution and
 * interpretation. The implementor must accomplish the request in a sandbox
 *
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TaskExecutor
 * @see pdp.compileplatform.frontend.RequestHandler
 * @see TaskQueue
 * @see TaskQueueInterface
 * @see pdp.compileplatform.frontend.Lang
 *  
 * 	
 *
 */

public interface TaskExecutorInterface{
	 
	/** Retrieve a task from the taskQueue and then execute it */
     public void executeTasks();
     
     /** Set the execution duration
      * 
      * @param timeInSec the duration in second
      */
     public void setExecutionTime(double timeInSec);
     /** Set the memory limit of the executor
      * 
      * @param memLimit the memory limit (optional suffix can be b,m or g) 
      * it must be a string representation of a number
      */
     public void setMemLimit(String memLimit);
     
     /** Retrieve all the languages currently supported by this executor
       * @return All the languages supported by this executor
      */
     public Lang[] getLanguages();   
}

