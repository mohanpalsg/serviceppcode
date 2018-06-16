package servicehandlercore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import commonfunctions.Alphafunctions;
import holderobjects.TickData;

import hibernateobjects.TickDataObject;

public class StrategyUpdater extends Alphafunctions implements java.lang.Runnable {

	private String duration;
	private SessionFactory factory;
	private Session session;
	@SuppressWarnings("deprecation")
	public StrategyUpdater(HashMap<String, String> setting )
	{
		super();
		
		this.duration = setting.get("WhoAmI");
		
		
	}
	public void updatestrategy(Session session,String symbol,String duration, ArrayList<TickData> tickhash)
	{

		//String symbol = (String)it.next();
		//ArrayList<TickData> tickhash = createTickFromDb(session,symbol,this.duration);
		if (tickhash.isEmpty())
			return;
		try {
			updateallstrategydata(tickhash,session,symbol,duration);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(duration)
		{
		case "Daily":
			updatepivot(tickhash, session,  symbol, "Daily");
		    break;
		case "Weekly":
			updatepivot(tickhash, session,  symbol, "Weekly");
			break;
		case "Monthly":
			updatepivot(tickhash, session,  symbol, "Monthly");
			ArrayList<TickData> yeartickhash = getYearTickhash(tickhash);
			if (yeartickhash ==null ||yeartickhash.isEmpty())
				return;
			updatepivot(yeartickhash,session,symbol,"YEAR");
			break;
			
		}
		/*
		tx  = session.beginTransaction();
		String qq = "delete from TickDataObject where stocksymbol='"+symbol+"' and duration='"+this.duration+"'";
		System.out.println(qq);
		Query q = session.createQuery(qq);
		q.executeUpdate();
	    tx.commit();
	    */
	
		
	}

	private void updateallstrategydata(ArrayList<TickData> tickhash, Session session, String symbol,
			String duration) throws Exception {
		
			 updatensebase(tickhash,session,symbol,duration);
		     tickhash=null;
		     System.gc();
		
		
	}
	
	/*
	public void run() {
		// TODO Auto-generated method stub
		
		
		
		Session session = this.factory.openSession();
		
		 List  list =null;
		 
		 Iterator it =null;
		
		  try {
			
			Transaction tx  = session.beginTransaction();
			
			list = session.createQuery("select distinct stocksymbol FROM Allstock").list();
			
			
			tx.commit();
			
			
			 it = list.iterator();
			 
			while (it.hasNext())
			{
				String symbol = (String)it.next();
				ArrayList<TickData> tickhash = createTickFromDb(session,symbol,this.duration);
				if (tickhash.isEmpty())
					continue;
				updatensebase(tickhash,session,symbol,this.duration);
				updatenseFinonacci(tickhash,session,symbol,this.duration);
				updatensetrend(tickhash,session,symbol,this.duration);
				updateHLWave(tickhash,session,symbol,this.duration);
				switch(this.duration)
				{
				case "Daily":
					updatepivot(tickhash, session,  symbol, "Daily");
				    break;
				case "Weekly":
					updatepivot(tickhash, session,  symbol, "Weekly");
					break;
				case "Monthly":
					updatepivot(tickhash, session,  symbol, "Monthly");
					ArrayList<TickData> yeartickhash = getYearTickhash(tickhash);
					if (yeartickhash ==null ||yeartickhash.isEmpty())
						return;
					updatepivot(yeartickhash,session,symbol,"YEAR");
					break;
					
				}
				
				tx  = session.beginTransaction();
				String qq = "delete from TickDataObject where stocksymbol='"+symbol+"' and duration='"+this.duration+"'";
				System.out.println(qq);
				Query q = session.createQuery(qq);
				q.executeUpdate();
			    tx.commit();
			   
			}
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
		finally
		{
		System.out.println("looped out");
	
		session.close();
		this.factory.close();
	   
		
		}
			
	}

	*/

	private ArrayList<TickData> createTickFromDb(Session session, String stocksymbol,String duration) {
		// TODO Auto-generated method stub
		Iterator tickit =null;
		List tickdata =null;
		ArrayList <TickData> result = new ArrayList <TickData>();
		Transaction tx= session.beginTransaction();
		tickdata =session.createQuery("from TickDataObject where stocksymbol='"+stocksymbol+"' and duration='"+duration+"' order by tickstart asc").list();
		tx.commit();
		tickit = tickdata.iterator();
		while (tickit.hasNext())
		{
			TickDataObject tickob = (TickDataObject) tickit.next();
			TickData td = new TickData();
			td.setTickstart(tickob.getTickstart());
			td.setTickend(tickob.getTickend());
			td.setCloseprice(tickob.getCloseprice());
			td.setHighprice(tickob.getHighprice());
			td.setLowprice(tickob.getLowprice());
			td.setOpenprice(tickob.getOpenprice());
			result.add(td);
		}
		return result;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
