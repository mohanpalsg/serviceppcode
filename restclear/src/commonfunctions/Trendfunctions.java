package commonfunctions;

import java.util.ArrayList;

import org.hibernate.Session;

import hibernateobjects.Defaultcomplexsetting;
import hibernateobjects.Nsetrend;
import holderobjects.TickData;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

public class Trendfunctions extends WaveFunctions {

	protected void updatensetrend(ArrayList<TickData> tickhash, Session session, String stocksymbol, String duration,float[] highArray,float[] lowArray,float[] closeArray) {
		// TODO Auto-generated method stub
		
		
	        Core c1 = new Core();
	        float [] sptnd = getsupertrend(closeArray,highArray,lowArray,c1,session,duration);
	        
	        Nsetrend nstrend = new Nsetrend();
	        nstrend.setDuration(duration);
	        nstrend.setStocksymbol(stocksymbol);
	        nstrend.setTrenddown(sptnd[0]);
	        nstrend.setTrendup(sptnd[1]);
	        if(sptnd[2] == -1)
	        	nstrend.setTrendstatus("down");
	        	  else if (sptnd[2] == 1)
	        		  nstrend.setTrendstatus("Up");
	        	  else
	        		  nstrend.setTrendstatus("NA");
	        System.out.println("before nsetrend update");
	        session.saveOrUpdate(nstrend);
	        System.out.println("After nsetrend update");
	        
	        updateHLWave(tickhash,session,stocksymbol,duration,highArray,lowArray,closeArray);
	}
	

	private float[] getsupertrend(float[] closeArray, float[] highArray, float[] lowArray, Core c, Session session,String duration) {
		// TODO Auto-generated method stub
		
		double[] out = new double[5000];
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        
		float up=0,dn=0;
		float trendup=0,trenddown=0,trend=0,atr=0;
		
		Defaultcomplexsetting st = new Defaultcomplexsetting();
		st.setSettingtype("TRN");
		st.setDuration(duration);
		
		Defaultcomplexsetting dcs = (Defaultcomplexsetting) session.get(Defaultcomplexsetting.class, st);
		
		int avg1=0,avg2=0,period=14;
		float adjustment=0f,factor=5f;
		
		if (dcs !=null)
		{
			period = Integer.parseInt(dcs.getPeriod());
		    factor = Float.valueOf(dcs.getFactor());
		}
		
		
		for(int i=0;i <closeArray.length;i++)
		{
			if(i>=14)
			{
			up = ((highArray[i]+lowArray[i])/2);
			dn = ((highArray[i]+lowArray[i])/2);
			RetCode retCode =c.atr(0, i, highArray, lowArray, closeArray, period, begin, length, out);
   		 if (retCode == RetCode.Success) {
   			 up = (float) (up-(factor*out[length.value-1]));
   			dn = (float) (dn+(factor*out[length.value-1]));
   			 
   		 }
   		 if (closeArray[i-1] > trendup )
   		 {
   			 if(up > trendup)
   				 trendup = up;
   		 }
   		 else
   			 trendup = up;
   		 
   		if (closeArray[i-1] < trenddown )
  		 {
  			 if (dn < trenddown)
  				 trenddown = dn;
  		 }
   		else
   			trenddown = dn;
   		 
   		if (closeArray[i] > trenddown)
   			trend =1;
   		if (closeArray[i]< trendup)
   			trend = -1;
   		
			}//if end
		}

return new float[] { trenddown, trendup,trend };
		
		
		
		//return (Double) null;
	}
}
