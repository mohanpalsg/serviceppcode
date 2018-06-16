package servicehandlercore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import hibernateobjects.Newphpdata;




public class Traderspitdownloader implements Runnable{
	
	private SessionFactory factory;
	private Session session;

	public Traderspitdownloader()
	{
		try {
			 this.factory =new Configuration().configure().buildSessionFactory();
			
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	}

	public void getmarketprice(String list) {
		// TODO Auto-generated method stub
		
		Session session = this.factory.openSession();
		
	   HashMap <String,hibernateobjects.Newphpdata> resultdata= new HashMap <String,Newphpdata>();
		BufferedReader in = null;
		String inputLine;
		boolean startmapping = false;
		try {
			URL tdlink = null;
			
				tdlink = new URL("https://nsertdata.000webhostapp.com/");
			
			URLConnection conn1 = tdlink.openConnection();
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
			while ((inputLine = in.readLine()) != null)
			{
				inputLine =inputLine.trim().replaceAll("\n ","");
				if(inputLine.isEmpty())
					continue;
			
				if(inputLine.equals("LINEHEADER"))
				{
					Newphpdata currdata = new Newphpdata();
					inputLine = in.readLine();
					currdata.setStocksymbol(inputLine);
					inputLine = in.readLine();
					currdata.setLastprice(Float.valueOf(inputLine));
					inputLine = in.readLine(); // change precentage
					inputLine = in.readLine();
					currdata.setOpenprice(Float.valueOf(inputLine)); //open
					inputLine = in.readLine();
					currdata.setHighprice(Float.valueOf(inputLine)); //high
					inputLine = in.readLine();
					currdata.setLowprice(Float.valueOf(inputLine)); //low
					inputLine = in.readLine();
					currdata.setPrevclose(Float.valueOf(inputLine));
					//inputLine = in.readLine();
					//currdata.setTradevolume(Long.valueOf(inputLine));
				//	resultdata.put(currdata.getStocksymbol(), currdata);
				//System.out.println(currdata.getStocksymbol()+":LTP"+currdata.getLastprice()+":open"+currdata.openprice
					//		+"high:"+currdata.highprice+"low:"+currdata.lowprice);
					
				try {
					
					Transaction tx  = session.beginTransaction();
					session.saveOrUpdate(currdata);
					tx.commit();
				}
				catch(Exception e) {
					
					System.out.println(e);
				}
				}
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
	}
	finally
	{
		
		session.close();
		this.factory.close();
	}

		
}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		getmarketprice("500");
	}
	
	
}
