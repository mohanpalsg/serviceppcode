package holderobjects;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutorInstance {

	private static ThreadExecutorInstance instance = null;
	private final ThreadPoolExecutor executorPool;
	
	protected ThreadExecutorInstance() {
	     // exService =  Executors.newFixedThreadPool(5, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(50));
	      executorPool = new ThreadPoolExecutor(1, 1, 500, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));   
	}
	
	public static ThreadExecutorInstance getInstance()
	{
		if(instance == null) {
	         instance = new ThreadExecutorInstance();
	      }
	      return instance;
	}
	
	public void addThread(Runnable runnable)
	{
		//exService.submit(runnable);
		executorPool.execute(runnable);
	}
}
