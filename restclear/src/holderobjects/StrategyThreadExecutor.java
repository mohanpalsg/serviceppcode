package holderobjects;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StrategyThreadExecutor {

	private static StrategyThreadExecutor instance = null;
	private final ThreadPoolExecutor executorPool;
	
	protected StrategyThreadExecutor() {
	     // exService =  Executors.newFixedThreadPool(5, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(50));
	      executorPool = new ThreadPoolExecutor(1, 1, 500, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));   
	}
	
	public static StrategyThreadExecutor getInstance()
	{
		if(instance == null) {
	         instance = new StrategyThreadExecutor();
	      }
	      return instance;
	}
	
	public void addThread(Runnable runnable)
	{
		//exService.submit(runnable);
		executorPool.execute(runnable);
	}
}
