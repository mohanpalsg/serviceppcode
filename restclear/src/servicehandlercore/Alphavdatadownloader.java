package servicehandlercore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import commonfunctions.Alphafunctions;
import hibernateobjects.Allstock;
import hibernateobjects.WsAuthenticationobject;
import holderobjects.StrategyThreadExecutor;
import holderobjects.TickData;
import springbeans.InvestmentStrategy1;


public class Alphavdatadownloader extends Alphafunctions implements java.lang.Runnable{
	private SessionFactory factory; 
	private String duration;
	private String key;
	private String functionparam;
	private String loadtype;
	private Long LTdiff =0L;
	HashMap <String,String> durationMap = new HashMap<String,String>();
	HashMap <String,String> settingString = new HashMap<String,String>();
	private Session session =null;
	StrategyUpdater st;
	public Alphavdatadownloader(HashMap<String, String> setting) {
		// TODO Auto-generated constructor stub
		
		super(setting.get("Load"));
		 st = new StrategyUpdater(setting);
		this.settingString = setting;
		try {
			 this.factory =new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		
		this.key=setting.get("AlphaKey");
		this.duration = setting.get("WhoAmI");
		this.functionparam = setting.get("Functionparam");
		this.loadtype = setting.get("Load");
		this.LTdiff = Long.valueOf(setting.get("loadtimediff"));
		
		System.out.println(this.key+" "+this.duration+" "+this.functionparam);
	}

	//https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=ITC.NS&apikey=8240&outputsize=full
	public  void run()
	{
		
		//System.out.println("inside thread");
		
		Session session = this.factory.openSession();
		
		List  list =null;
		try {
			
			Transaction tx  = session.beginTransaction();
			
			list = session.createQuery("select distinct stocksymbol FROM Allstock").list();
			
			
			tx.commit();
			
			//session1 = factory.openSession();
			//System.out.println("txend");
			//System.out.println("listsize "+list.size());
			Iterator it = list.iterator();
			while (it.hasNext())
			{
				//System.out.println("entering list");
				
				updatetickdata((String)it.next(),this.duration,session,st);
				Thread.sleep(1500);
				System.out.println("looping");
			}
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
		
		/*
		finally
		{
		System.out.println("looped out");
		StrategyThreadExecutor tEI = StrategyThreadExecutor.getInstance();
		tEI.addThread(new StrategyUpdater(this.settingString));
		session.close();
		this.factory.close();
		}
		*/
		//
		
		//return;
	}

	private   void updatetickdata(String stocksymbol, String duration, Session session, StrategyUpdater st) {
		// TODO Auto-generated method stub
		
		
		
		
		
		
try {
							
			updateduration(stocksymbol,session,duration,st);
			
		
		}
		catch (Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	
		
		
		
	}

	

	private void updateduration(String stocksymbol, Session session, String duration, StrategyUpdater st) {
		// TODO Auto-generated method stub
		
		Allstock basestock = new Allstock();
		basestock.setStocksymbol(stocksymbol);
		basestock.setDuration(duration);
		Allstock allstk = (Allstock) session.get(Allstock.class,basestock);
		//System.out.println("entering "+duration);
		
			  proceedProc(stocksymbol,session,duration,allstk,this.LTdiff,this.functionparam,st);
			
		}
		
	  
	             	
	
			
		
	

	

	protected void proceedProc(String stocksymbol, Session session, String duration, Allstock allstk, Long lTdiff2, String timeseries, StrategyUpdater st) {
		// TODO Auto-generated method stub

		ArrayList <TickData> tickhash = new ArrayList <TickData> ();
		if (Math.abs(new Date().getTime() - allstk.getLastupdate().getTime()) > lTdiff2)
		
		{
	try
	{
		Transaction td = session.beginTransaction();
		String qq = "delete from TickDataObject where stocksymbol='"+stocksymbol+"' and duration='"+duration+"'";
		System.out.println(qq);
		Query q = session.createQuery(qq);
		q.executeUpdate();
	
		td.commit();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
			
			
			switch(duration)
			{
			case "5min":
			case "15min":
			case "30min":
			case "60min":
				tickhash = getAlphaintradaydata(stocksymbol,timeseries,this.key,duration);
				System.out.println(tickhash.size());
				break;
			case "Daily":
				//System.out.println("entering this");
				tickhash = getAplhatickdata(stocksymbol,timeseries,this.key);
				System.out.println(tickhash.size());
				break;
			case "Weekly":
				tickhash = getAplhaweektickdata(stocksymbol,timeseries,this.key);
				break;
			case "Monthly":
				tickhash = getAplhamonthtickdata(stocksymbol,timeseries,this.key);
				break;
			}

			
			if (checkTick(tickhash,duration,allstk,stocksymbol,session) == 0)
				return;
			//else
				//updatenormaltdata(tickhash,session,duration,stocksymbol,st);
		}

st.updatestrategy(session, stocksymbol, duration,tickhash);
		
	}

	

	
	
	
	}


