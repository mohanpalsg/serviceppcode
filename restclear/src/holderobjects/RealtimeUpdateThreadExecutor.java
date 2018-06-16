package holderobjects;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RealtimeUpdateThreadExecutor {



	private static RealtimeUpdateThreadExecutor instance = null;
	private static Date lastime = new Date();
	private final ThreadPoolExecutor executorPool;
	
	protected RealtimeUpdateThreadExecutor() {
	     // exService =  Executors.newFixedThreadPool(5, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(50));
	      executorPool = new ThreadPoolExecutor(1, 1, 500, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));   
	}
	
	public static RealtimeUpdateThreadExecutor getInstance()
	{
		if(instance == null) {
	         instance = new RealtimeUpdateThreadExecutor();
	      }
	      return instance;
	}
	
	public void addThread(Runnable runnable)
	{
		//exService.submit(runnable);
		if(new Date().getTime()-lastime.getTime() > 120000)
		{
			this.lastime = new Date();
		executorPool.execute(runnable);
		System.out.println("realtime Update happening");
		}
		else
		{
			System.out.println("skipped realtime Update");
		}
	}

}
