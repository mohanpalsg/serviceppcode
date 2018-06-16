package commonfunctions;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.hibernate.Session;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import hibernateobjects.Defaultcomplexsetting;
import hibernateobjects.NseWaveData;
import holderobjects.TickData;

public class WaveFunctions {

	

	public void updateHLWave(ArrayList<TickData> tickhash, Session session, String symbol, String duration,float[] highArray,float[] lowArray,float[] closeArray) {
		// TODO Auto-generated method stub
		
		
		
	        
	        Core c1 = new Core();
	        double[] wavedata = getWaveData(closeArray,highArray,lowArray,c1,session,duration);
		
	        NseWaveData nw = new NseWaveData();
	        nw.setStocksymbol(symbol);
	        nw.setDuration(duration);
	        nw.setSupport(wavedata[0]);
	        nw.setResistance(wavedata[1]);
	        if(nw.getSupport() >0 || nw.getResistance() >0)
	        session.saveOrUpdate(nw);
		
		
	}

	private double[] getWaveData(float[] closeArray, float[] highArray, float[] lowArray, Core c1, Session session,
			String duration) {
		// TODO Auto-generated method stub
		
		// period = 125 or 150 fecth from settings
				// factor = 2 fecth from settings
				// lowband = low - factor*(stdev(low,period));
				// highband = high + factor*(stdev(high,period));
				
				//support = sma(lowband,period)
				//resistance = sma(highband,period)
		
		double[] out = new double[5000];
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        DecimalFormat df1 = new DecimalFormat("#.##"); 
        
		Defaultcomplexsetting st = new Defaultcomplexsetting();
		st.setSettingtype("WAV");
		st.setDuration(duration);
		
Defaultcomplexsetting dcs = (Defaultcomplexsetting) session.get(Defaultcomplexsetting.class, st);
		
		int avg1=0,avg2=0,period=14;
		double adjustment=0f,factor=5f;
		ArrayList<Float> lowband = new ArrayList<Float>();
		ArrayList<Float> highband = new ArrayList<Float>();
		RetCode retCode;
		if (dcs !=null)
		{
			period = Integer.parseInt(dcs.getPeriod());
		    factor = Double.valueOf(dcs.getFactor());
		}
		
		for(int i=0;i <lowArray.length;i++)
		{
			if(i>period)
			{
			 retCode =c1.stdDev(0, i, lowArray, period, factor, begin, length, out);
			
			 if (retCode == RetCode.Success) {
	   			 
	   			float lb = (float) ((float)lowArray[i]- out[length.value-1]);
	   			lowband.add(lb);
	   		 }	
			}
		}
		for(int i=0;i <highArray.length;i++)
		{
			if(i > period)
			{
			 retCode =c1.stdDev(0, i, highArray, period, factor, begin, length, out);
			
			 if (retCode == RetCode.Success) {
	   			 
	   			float lb = (float) ((float)highArray[i]+ out[length.value-1]);
	   			highband.add(lb);
	   		 }		
			}
		}
		
		float[] lowbandArray = new float[lowband.size()];
		float[] highbandArray = new float[highband.size()];
		int k=0;
		for (Float f : lowband) {
			lowbandArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
		k=0;
		for (Float f : highband) {
			highbandArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
		lowband=null;
		highband=null;
		double smalowband =0,smahighband=0;
		try
		{
		 retCode = c1.sma(0, lowbandArray.length-1, lowbandArray, period, begin, length, out);
		 if (retCode == RetCode.Success) {
   			 
	   			smalowband = Double.valueOf(df1.format(out[length.value-1]));
	   		 }	
		}
		catch(Exception e)
		{
			
		}
		try
		{
		 retCode = c1.sma(0, highbandArray.length-1, highbandArray, period, begin, length, out);
		 if (retCode == RetCode.Success) {
   			 
	   			smahighband = Double.valueOf(df1.format(out[length.value-1]));
	   		 }	
		}
		catch(Exception e)
		{
			
		}
		highbandArray=null;
		lowbandArray=null;
		
		 return new double[] { smalowband, smahighband };
	}

}
