package commonfunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernateobjects.Allstock;
import hibernateobjects.TickDataObject;
import holderobjects.TickData;
import javassist.bytecode.Descriptor.Iterator;
import servicehandlercore.StrategyUpdater;

public class Alphafunctions extends Pivotfunctions{

private HashMap <String,String> Intrayday_durationstart = new HashMap <String,String>();
private String loadtype;

   
	public Alphafunctions(String loadtype) {
	
		this();
		this.loadtype=loadtype;
		
}
	public Alphafunctions() {
		Intrayday_durationstart.put("5min", "Time Series (5min)");
		Intrayday_durationstart.put("15min", "Time Series (15min)");
		Intrayday_durationstart.put("30min", "Time Series (30min)");
		Intrayday_durationstart.put("60min", "Time Series (60min)");
	}
	protected void updatenormaltdata(ArrayList<TickData> tickhash, Session session, String duration,
			String stocksymbol, StrategyUpdater st) {
		Transaction tx = session.beginTransaction();
		System.out.println("Entering First insert"+ new Date());
		for(TickData tk : tickhash)
		{
			TickDataObject td = new TickDataObject(tk);
			td.setStocksymbol(stocksymbol);
			td.setDuration(duration);
		//	System.out.println("Calling save"+ new Date());
			
			session.save(td);
			
		//	System.out.println("save Complete"+ new Date());
			
		}
		tx.commit();
		
		System.out.println("Exit Last insert"+ new Date());
	}


	protected int checkTick(ArrayList<TickData> tickhash, String duration, Allstock allstk, String stocksymbol, Session session) {
		// TODO Auto-generated method stub
		
		float prev_open=0f,prev_close=0f,prev_low=0f,prev_high=0f,lastprice=0f,openprice=0f,highprice=0f,lowprice=0f;
		Date lasttradingday;
		

		
		if (tickhash == null || tickhash.isEmpty())
			return 0;
		TickData inter = (TickData)tickhash.get(tickhash.size()-1);
	     lastprice = inter.getCloseprice();
	     openprice = inter.getOpenprice();
	     highprice = inter.getHighprice();
	     lowprice = inter.getLowprice();
	     lasttradingday = inter.getTickstart();
	    
	    
	    if (tickhash.size()>2)
	    {
	    	inter = (TickData)tickhash.get(tickhash.size()-2);
	    	prev_open = inter.getOpenprice();
	    	prev_close = inter.getCloseprice();
	    	prev_low = inter.getLowprice();
	    	prev_high = inter.getHighprice();
	    	
	    }
	   
		allstk.setLastupdate(new Date());
		allstk.setLastprice(lastprice);
		allstk.setLasttradingday(lasttradingday);
		allstk.setOpenprice(openprice);
		allstk.setLowprice(lowprice);
		allstk.setHighprice(highprice);
		allstk.setDuration(duration);
		allstk.setPrev_closeprice(prev_close);
		allstk.setPrev_highprice(prev_high);
		allstk.setPrev_openprice(prev_open);
		allstk.setPrev_lowprice(prev_low);
		
		Transaction td = session.beginTransaction();
		session.saveOrUpdate(allstk);
		td.commit();
		return 1;
	}

	
	protected ArrayList<TickData> getAlphaintradaydata(String stocksymbol, String functionparam, String key,
			String duration) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		try {
			TimeUnit.SECONDS.sleep(1L);
		} catch (InterruptedException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		String pattern = "^[a-zA-Z0-9_ ]*$";
		 Pattern r = Pattern.compile(pattern);
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ArrayList<TickData> tickhash = new ArrayList<TickData> ();
		String date = null,open=null,high=null,low=null,close=null,adjusted_close,volume=null,divamt,splitcoeff;
        String startstring = Intrayday_durationstart.get(duration);
		URL tdlink = null;
		try {
			// loadtype accepted values full or compact.
			tdlink = new URL("https://www.alphavantage.co/query?function="+functionparam+"&symbol="+stocksymbol+".NS&interval="+duration+"&apikey="+key+"&outputsize="+this.loadtype);
	
			System.out.println(tdlink.toString());
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		URLConnection conn1 = null;
		try {
			conn1 = tdlink.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		StringBuilder responseStrBuilder = new StringBuilder();

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		String inputStr;
		boolean start = false;
		boolean skip = false;
		boolean lineend = false;
		int position=0;
	    try {
			while ((inputStr = in.readLine()) != null)
			    {
				
				if (inputStr.contains(startstring))
						start= true;
						
				if (start && !skip)
				{
					skip = true;
					continue;
				}
				
				if (start && skip && !inputStr.trim().startsWith("}"))
				{
					lineend = false;
					//0. date. "2018-04-04": {
					switch (position)
					{
					case 0: //0. date. "2018-04-04": {
						//System.out.println(inputStr.trim());
						//System.out.println("case 0");
					date = getjsondate(inputStr.trim(),r);
					position++;
					break;
					
					case 1: //"1. open": "17.2433",
						open = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 2: //"2. high": "15.7444",
						high = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 3: //"3. low": "15.0000",
						low = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 4: //"4. close": "15.7444",
						close = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 5: // "5. adjusted close": "0.0008",
						volume = getjsonvalue(inputStr.trim());	
						position=0;
						lineend=true;
			    		break;
			    			
					}
					
				}
				else if (start && skip && inputStr.trim().startsWith("}") && lineend)
				{
					lineend =false;
					//push to object
				//	if(date.startsWith("2018"))
					//System.out.println(date +" "+open+" "+high+" "+low+" "+close+volume);
					
					TickData tick = new TickData();
					try {
						tick.setTickstart(sf.parse(date));
						tick.setTickend(sf.parse(date));
						tick.setHighprice(Float.valueOf(high));
						tick.setLowprice(Float.valueOf(low));
						tick.setCloseprice(Float.valueOf(close));
						tick.setOpenprice(Float.valueOf(open));
						tick.setVolume(Long.valueOf(volume));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					tickhash.add(tick);
					
				}
			    }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.reverse(tickhash);
        
	   if (tickhash.isEmpty())
		   tickhash = getAlphaintradaydata_Alternate(stocksymbol, functionparam, key,
					duration);
		return tickhash;
	   
		
	
	}
	

	
	private ArrayList<TickData> getAlphaintradaydata_Alternate(String stocksymbol, String functionparam, String key, String duration) {

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		try {
			TimeUnit.SECONDS.sleep(1L);
		} catch (InterruptedException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		// TODO Auto-generated method stub
		System.out.println("entering alternate");
		String pattern = "^[a-zA-Z0-9_ ]*$";
		 Pattern r = Pattern.compile(pattern);
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ArrayList<TickData> tickhash = new ArrayList<TickData> ();
		String date = null,open=null,high=null,low=null,close=null,adjusted_close,volume=null,divamt,splitcoeff;
        String startstring = Intrayday_durationstart.get(duration);
		URL tdlink = null;
		try {
			tdlink = new URL("https://www.alphavantage.co/query?function="+functionparam+"&symbol="+stocksymbol+"&interval="+duration+"&apikey="+key+"&outputsize="+this.loadtype);
			System.out.println(tdlink.toString());
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		URLConnection conn1 = null;
		try {
			conn1 = tdlink.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		StringBuilder responseStrBuilder = new StringBuilder();

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		String inputStr;
		boolean start = false;
		boolean skip = false;
		boolean lineend = false;
		int position=0;
	    try {
			while ((inputStr = in.readLine()) != null)
			    {
				
				if (inputStr.contains(startstring))
						start= true;
						
				if (start && !skip)
				{
					skip = true;
					continue;
				}
				
				if (start && skip && !inputStr.trim().startsWith("}"))
				{
					lineend = false;
					//0. date. "2018-04-04": {
					switch (position)
					{
					case 0: //0. date. "2018-04-04": {
						//System.out.println(inputStr.trim());
						//System.out.println("case 0");
					date = getjsondate(inputStr.trim(),r);
					position++;
					break;
					
					case 1: //"1. open": "17.2433",
						open = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 2: //"2. high": "15.7444",
						high = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 3: //"3. low": "15.0000",
						low = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 4: //"4. close": "15.7444",
						close = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 5: // "5. adjusted close": "0.0008",
						volume = getjsonvalue(inputStr.trim());	
						position=0;
						lineend=true;
			    		break;
			    			
					}
					
				}
				else if (start && skip && inputStr.trim().startsWith("}") && lineend)
				{
					lineend =false;
					//push to object
				//	if(date.startsWith("2018"))
					//System.out.println(date +" "+open+" "+high+" "+low+" "+close+volume);
					
					TickData tick = new TickData();
					try {
						tick.setTickstart(sf.parse(date));
						tick.setTickend(sf.parse(date));
						tick.setHighprice(Float.valueOf(high));
						tick.setLowprice(Float.valueOf(low));
						tick.setCloseprice(Float.valueOf(close));
						tick.setOpenprice(Float.valueOf(open));
						tick.setVolume(Long.valueOf(volume));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					tickhash.add(tick);
					
				}
			    }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.reverse(tickhash);
        
	 
		return tickhash;
	 
		
	
	
		
	}
	private String getjsondate(String trim, Pattern r) {
		// TODO Auto-generated method stub
		trim = trim.trim();
		//System.out.println(trim);
		//System.out.println(trim.indexOf("\""));
		trim=trim.substring(trim.indexOf("\"")+1,trim.length());
		//System.out.println(trim);
		trim = trim.substring(0,trim.indexOf("\""));
		//System.out.println(trim);
		return trim.trim();
		
		
	}
	protected ArrayList<TickData> getYearTickhash(ArrayList<TickData> monthtickhash) {
		// TODO Auto-generated method stub
		ArrayList<TickData> yeartick = new ArrayList<TickData> ();
		int i= monthtickhash.size()-26;
		boolean yearfirsttick = false;
		 if (i < 0) return null;
		 TickData year = new TickData();
		while (i < monthtickhash.size())
		{
			
			TickData td = monthtickhash.get(i);
			//System.out.println(td.getTickstart()+":"+td.getTickstart().getMonth() +":"+td.getTickstart().getYear());
			if (td.getTickstart().getMonth()==0 && td.getTickstart().getYear()==(new Date().getYear()-1))
			{
				yearfirsttick = true;
				year.setTickstart(td.getTickstart());
				year.setCloseprice(td.getCloseprice());
				year.setHighprice(td.getHighprice());
				year.setLowprice(td.getLowprice());
				year.setOpenprice(td.getOpenprice());
				year.setVolume(td.getVolume());
				
			}
			if (yearfirsttick && !(td.getTickstart().getMonth()==11 && td.getTickstart().getYear()==(new Date().getYear()-1)))
			{
				if (td.getHighprice() > year.getHighprice())
					year.setHighprice(td.getHighprice());
				if (td.getLowprice() < year.getLowprice())
					year.setLowprice(td.getLowprice());
				year.setVolume(year.getVolume()+td.getVolume());
			}
			if (yearfirsttick && (td.getTickstart().getMonth()==11 && td.getTickstart().getYear()==(new Date().getYear()-1)))
			{
				if (td.getHighprice() > year.getHighprice())
					year.setHighprice(td.getHighprice());
				if (td.getLowprice() < year.getLowprice())
					year.setLowprice(td.getLowprice());
				year.setVolume(year.getVolume()+td.getVolume());
				year.setCloseprice(td.getCloseprice());
				yeartick.add(year);
				yearfirsttick=false;
			}
			
			i++;
		}
		return yeartick;
	}
	
	protected ArrayList<TickData> getAplhamonthtickdata(String stocksymbol, String functionparam, String key) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");  
		ArrayList<TickData> tickhash = new ArrayList<TickData> ();
		String date = null,open=null,high=null,low=null,close=null,adjusted_close,volume=null,divamt,splitcoeff;

		URL tdlink = null;
		try {
			tdlink = new URL("https://www.alphavantage.co/query?function="+functionparam+"&symbol="+stocksymbol+".NS&apikey="+key+"&outputsize=full");
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		URLConnection conn1 = null;
		try {
			conn1 = tdlink.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		StringBuilder responseStrBuilder = new StringBuilder();

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		String inputStr;
		boolean start = false;
		boolean skip = false;
		boolean lineend = false;
		int position=0;
	    try {
			while ((inputStr = in.readLine()) != null)
			    {
				
				if (inputStr.contains("Monthly Adjusted Time Series"))
						start= true;
						
				if (start && !skip)
				{
					skip = true;
					continue;
				}
				
				if (start && skip && !inputStr.trim().startsWith("}"))
				{
					lineend = false;
					//0. date. "2018-04-04": {
					switch (position)
					{
					case 0: //0. date. "2018-04-04": {
						//System.out.println(inputStr.trim());
						//System.out.println("case 0");
					date = getjsondate(inputStr.trim());
					position++;
					break;
					
					case 1: //"1. open": "17.2433",
						open = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 2: //"2. high": "15.7444",
						high = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 3: //"3. low": "15.0000",
						low = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 4: //"4. close": "15.7444",
						close = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 5: // "5. adjusted close": "0.0008",
						position++;
			    		break;
			    		
					case 6: // "6. volume": "25322175",
						volume = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 7: // "7. dividend amount": "0.0000",
						position=0;
						lineend=true;
			    		break;
			    		
						
			    		
					
						
					}
					
				}
				else if (start && skip && inputStr.trim().startsWith("}") && lineend)
				{
					lineend =false;
					//push to object
				//	if(date.startsWith("2018"))
					//System.out.println(date +" "+open+" "+high+" "+low+" "+close+volume);
					
					TickData tick = new TickData();
					try {
						tick.setTickstart(sf.parse(date));
						tick.setTickend(sf.parse(date));
						tick.setHighprice(Float.valueOf(high));
						tick.setLowprice(Float.valueOf(low));
						tick.setCloseprice(Float.valueOf(close));
						tick.setOpenprice(Float.valueOf(open));
						tick.setVolume(Long.valueOf(volume));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					tickhash.add(tick);
					
				}
			    }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.reverse(tickhash);
        
	
		return tickhash;
	
	
	}
	
	protected ArrayList<TickData> getAplhaweektickdata(String stocksymbol, String functionparam, String key) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");  
		ArrayList<TickData> tickhash = new ArrayList<TickData> ();
		String date = null,open=null,high=null,low=null,close=null,adjusted_close,volume=null,divamt,splitcoeff;

		URL tdlink = null;
		try {
			tdlink = new URL("https://www.alphavantage.co/query?function="+functionparam+"&symbol="+stocksymbol+".NS&apikey="+key+"&outputsize=full");
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		URLConnection conn1 = null;
		try {
			conn1 = tdlink.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		StringBuilder responseStrBuilder = new StringBuilder();

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		String inputStr;
		boolean start = false;
		boolean skip = false;
		boolean lineend = false;
		int position=0;
	    try {
			while ((inputStr = in.readLine()) != null)
			    {
				
				if (inputStr.contains("Weekly Adjusted Time Series"))
						start= true;
						
				if (start && !skip)
				{
					skip = true;
					continue;
				}
				
				if (start && skip && !inputStr.trim().startsWith("}"))
				{
					lineend = false;
					//0. date. "2018-04-04": {
					switch (position)
					{
					case 0: //0. date. "2018-04-04": {
						//System.out.println(inputStr.trim());
						//System.out.println("case 0");
					date = getjsondate(inputStr.trim());
					position++;
					break;
					
					case 1: //"1. open": "17.2433",
						open = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 2: //"2. high": "15.7444",
						high = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 3: //"3. low": "15.0000",
						low = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 4: //"4. close": "15.7444",
						close = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 5: // "5. adjusted close": "0.0008",
						position++;
			    		break;
			    		
					case 6: // "6. volume": "25322175",
						volume = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 7: // "7. dividend amount": "0.0000",
						position=0;
						lineend=true;
			    		break;
			    		
						
			    		
					
						
					}
					
				}
				else if (start && skip && inputStr.trim().startsWith("}") && lineend)
				{
					lineend =false;
					//push to object
				//	if(date.startsWith("2018"))
					//System.out.println(date +" "+open+" "+high+" "+low+" "+close+volume);
					
					TickData tick = new TickData();
					try {
						tick.setTickstart(sf.parse(date));
						tick.setTickend(sf.parse(date));
						tick.setHighprice(Float.valueOf(high));
						tick.setLowprice(Float.valueOf(low));
						tick.setCloseprice(Float.valueOf(close));
						tick.setOpenprice(Float.valueOf(open));
						tick.setVolume(Long.valueOf(volume));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					tickhash.add(tick);
					
				}
			    }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.reverse(tickhash);
        
	
		return tickhash;
	
	}
	
	public ArrayList<TickData> getAplhatickdata(String stocksymbol, String functionparam, String key) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
		 
		 
		ArrayList<TickData> tickhash = new ArrayList<TickData> ();
		String date = null,open=null,high=null,low=null,close=null,adjusted_close,volume=null,divamt,splitcoeff;

		URL tdlink = null;
		try {
			tdlink = new URL("https://www.alphavantage.co/query?function="+functionparam+"&symbol="+stocksymbol+".NS&apikey="+key+"&outputsize=full");
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		URLConnection conn1 = null;
		try {
			conn1 = tdlink.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		StringBuilder responseStrBuilder = new StringBuilder();

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		String inputStr;
		boolean start = false;
		boolean skip = false;
		boolean lineend = false;
		int position=0;
	    try {
			while ((inputStr = in.readLine()) != null)
			    {
				
				if (inputStr.contains("Time Series (Daily)"))
						start= true;
						
				if (start && !skip)
				{
					skip = true;
					continue;
				}
				
				if (start && skip && !inputStr.trim().startsWith("}"))
				{
					lineend = false;
					//0. date. "2018-04-04": {
					switch (position)
					{
					case 0: //0. date. "2018-04-04": {
						//System.out.println(inputStr.trim());
						//System.out.println("case 0");
					date = getjsondate(inputStr.trim());
					position++;
					break;
					
					case 1: //"1. open": "17.2433",
						open = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 2: //"2. high": "15.7444",
						high = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 3: //"3. low": "15.0000",
						low = getjsonvalue(inputStr.trim());
						position++;
			    		break;
			    		
					case 4: //"4. close": "15.7444",
						close = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 5: // "5. adjusted close": "0.0008",
						position++;
			    		break;
			    		
					case 6: // "6. volume": "25322175",
						volume = getjsonvalue(inputStr.trim());	
						position++;
			    		break;
			    		
					case 7: // "7. dividend amount": "0.0000",
						position++;
			    		break;
			    		
					case 8: // "8. split coefficient": "1.0000"
						position=0;
						lineend=true;
			    		break;
			    		
					
						
					}
					
				}
				else if (start && skip && inputStr.trim().startsWith("}") && lineend)
				{
					lineend =false;
					//push to object
				//	if(date.startsWith("2018"))
					//System.out.println(date +" "+open+" "+high+" "+low+" "+close+volume);
					
					TickData tick = new TickData();
					try {
						tick.setTickstart(sf.parse(date));
						tick.setTickend(sf.parse(date));
						tick.setHighprice(Float.valueOf(high));
						tick.setLowprice(Float.valueOf(low));
						tick.setCloseprice(Float.valueOf(close));
						tick.setOpenprice(Float.valueOf(open));
						tick.setVolume(Long.valueOf(volume));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					tickhash.add(tick);
					
				}
			    }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Collections.reverse(tickhash);
        
	
		return tickhash;
	}
	
	protected static String getjsondate(String trim1) {
		// TODO Auto-generated method stub
		
		String tm = trim1.trim();
		//System.out.println(tm);
		tm = tm.replace("\"", "");
		//System.out.println(tm);
		tm= tm.replace(":", "");
		//System.out.println(tm);
		tm=tm.replace("{", "");
		return tm.trim();
	}

	protected static String getjsonvalue(String trim) {
		// TODO Auto-generated method stub
		trim=trim.trim();
		StringTokenizer st = new StringTokenizer(trim,":");
		st.nextToken();
		return st.nextToken().replaceAll("\"", "").replaceAll(",", "").trim();
	}

}
