package commonfunctions;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.hibernate.Session;

import hibernateobjects.Defaultsimplesetting;
import hibernateobjects.Nsebase;
import holderobjects.TickData;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

public class Basefunctions extends Fibonaccifunctions{

	protected void updatensebase(ArrayList<TickData> tickhash, Session session, String stocksymbol, String duration) throws Exception {
		// TODO Auto-generated method stub
		Nsebase nsebase= new Nsebase();
		ArrayList<TickData> Converttick = new ArrayList<TickData>();
		ArrayList<Float> low = new ArrayList<Float>();
        ArrayList<Float> high = new ArrayList<Float>();
        ArrayList<Float> obvvol = new ArrayList<Float>();
        ArrayList<Float> close = new ArrayList<Float>();
        Float obvvolume = 0f, prevclose = 0f;
        Converttick = tickhash;
        
       
        
        for (int ik =0;ik < Converttick.size();ik++)
        {
        	
        	TickData Currtickdata = Converttick.get(ik);
        	
			if (ik > 0)
        	{
				//System.out.println(Currtickdata.getTickstart());
        		TickData prevtickdata = Converttick.get(ik-1);
        		prevclose = prevtickdata.getCloseprice();
        		obvvolume=getobvvol(Currtickdata.getHighprice(),Currtickdata.getLowprice(),Currtickdata.getCloseprice(),Currtickdata.getVolume(),obvvolume,prevclose);
        	}
        	else
        	{
        		//System.out.println(Currtickdata.getTickstart());
        		prevclose = Currtickdata.getCloseprice();
        		obvvolume = Currtickdata.getVolume();
        	}
        	
        	
        	
        	low.add(Currtickdata.getLowprice());
        	high.add(Currtickdata.getHighprice());
        	close.add(Currtickdata.getCloseprice());
        	
        	obvvol.add(obvvolume);
        	
        	
        }
       
        
        float[] lowArray = new float[low.size()];
        int k = 0;

        for (Float f : low) {
        	lowArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        
        float[] highArray = new float[high.size()];
        k = 0;

        for (Float f : high) {
        	highArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        
        float[] closeArray = new float[close.size()];
        k = 0;

        for (Float f : close) {
        	closeArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        
        low=null;
        high=null;
        close=null;
        
        float[] obvArray = new float[obvvol.size()];
       k = 0;

        for (Float f : obvvol) {
        	obvArray[k++] = f; // Or whatever default you want.
        }
        
        Float lowdiff;
        
        
        Float highdiff;
       
      
       
        Core c1 = new Core();
      
        double[] out = new double[5000];
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        
        ArrayList<Float> lowdiffarr = new ArrayList<Float>();
        ArrayList<Float> highdiffarr = new ArrayList<Float>();
        
        	
        		 RetCode retCode =c1.wma(0, lowArray.length-1, lowArray, 50, begin, length, out) ;
        		 if (retCode == RetCode.Success) {
        			 for (int i = begin.value; i < length.value+begin.value; i++) {
        				 lowdiff = lowArray[i] - Float.valueOf(String.valueOf(out[i-begin.value]));
            			// System.out.println(i+":"+lowdiff+";"+lowArray[i]+"::"+out[i-begin.value]);
            			 lowdiffarr.add(lowdiff);
        			 }
        		 }
        			
        			 
        		 retCode =c1.wma(0, highArray.length-1, highArray, 50, begin, length, out) ;
        		 if (retCode == RetCode.Success) {
        			 for (int i = begin.value; i < length.value+begin.value; i++) {
        				 highdiff = highArray[i] - Float.valueOf(String.valueOf(out[i-begin.value]));
            			 
            			 highdiffarr.add(highdiff);
        			 }
        		 }
        			
        		 
        		
        		
        		 
        	
        	
        	float[] highdiffArray = new float[highdiffarr.size()];
            k = 0;

            for (Float f : highdiffarr) {
            	highdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
            } 
            
        	float[] lowdiffArray = new float[lowdiffarr.size()];
            k = 0;

            for (Float f : lowdiffarr) {
            	lowdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
            } 
            
            highdiffarr=null;
            lowdiffarr=null;
          
            
        // lowdiffArray, highdiffarray, obvArray,lowarray ,closeArrayready
        	
            DecimalFormat df1 = new DecimalFormat("#.##"); 
        
          
          double [] vals = getstochvals(lowdiffArray,highdiffArray,obvArray,lowArray,retCode,c1);
         
          Double indick = Double.valueOf(df1.format(vals[0]));
          Double indicd = Double.valueOf(df1.format(vals[1]));
          Double wpr = null;
		 
          lowdiffArray=null;
          highdiffArray=null;
          int wpr_period = 21;
          Defaultsimplesetting dst = (Defaultsimplesetting) session.get(Defaultsimplesetting.class, "willr_p");
          if (dst !=null)
        	  wpr_period= Integer.parseInt(dst.getValue());
          
          
           retCode = c1.willR(0, closeArray.length-1, highArray, lowArray, closeArray, wpr_period, begin, length, out);
           if (retCode == RetCode.Success) {
        	   
			try{
        	   wpr= Double.valueOf(df1.format(out[length.value-1]));
        	   }
        	   catch(Exception e)
        	   {
        		   wpr=(double) 75;
        	   }
           }
           
           int rsi_period = 14;
           dst = (Defaultsimplesetting) session.get(Defaultsimplesetting.class, "rsi_p");
           if (dst !=null)
         	  rsi_period= Integer.parseInt(dst.getValue());
           
		 
           retCode = c1.rsi(0, closeArray.length-1, closeArray, rsi_period, begin, length, out);
           Double rsi = null;
  if (retCode == RetCode.Success) {
	 
	try{
	  rsi= Double.valueOf(df1.format(out[length.value-1]));
	  }
	  catch(Exception e)
	  {
		  rsi=(double) 75;
	  }
        	   
           }
  double sma200 = 0;
  retCode = c1.sma(0, closeArray.length-1, closeArray, 200, begin, length, out);

  if (retCode == RetCode.Success) {
	  
	try{
	  sma200= Double.valueOf(df1.format(out[length.value-1]));
	  }
	  catch(Exception e)
	  {
		  
		  sma200=(double) 0;
	  }
        	   
           }
  
  double sma50 = 0;
  retCode = c1.sma(0, closeArray.length-1, closeArray, 50, begin, length, out);

  if (retCode == RetCode.Success) {
	  
	try{
	  sma50= Double.valueOf(df1.format(out[length.value-1]));
	  }
	  catch(Exception e)
	  {
		  
		  sma50=(double) 0;
	  }
        	   
           }
  
  nsebase.setDuration(duration);
 
  nsebase.setStocksymbol(stocksymbol);
  nsebase.setSma50(sma50);
  System.out.println(sma50);
  nsebase.setSma200(sma200);
  System.out.println(sma200);
  nsebase.setRsi(rsi);
  System.out.println(rsi);
  nsebase.setWillr(wpr);
  System.out.println(wpr);
  nsebase.setStochk(indick);
  System.out.println(indick);
  nsebase.setStockd(indicd);
  System.out.println(indicd);
  System.out.println("nsebase before commit");
  session.saveOrUpdate(nsebase);
  System.out.println("nsebase after commit");
  
 
  
  
  updatenseFinonacci(tickhash,session,stocksymbol,duration,highArray,lowArray,closeArray);
	highArray=null;
	lowArray=null;
	closeArray=null;
	
	}
	
	

	private static Float getobvvol(Float c_highprice, Float c_lowprice, Float c_closeprice, Float c_currentvolume,
			Float obvvolume, Float closeprice) {
		// TODO Auto-generated method stub
		
		Float range = (c_highprice+c_lowprice)/2;
		if (c_closeprice >= range)
		{
			if (c_closeprice >= closeprice)
			{
				return (float) (obvvolume + c_currentvolume);
			}
			else
			{
				return obvvolume;
			}
		}
		else
		{
			if (c_closeprice >= closeprice)
			{
				return obvvolume ;
			}
			else
			{
				return obvvolume - c_currentvolume;
			}
		}
		
	}
	
	private static double[] getstochvals(float[] lowdiffArray, float[] highdiffArray, float[] obvArray, float[] lowArray,
			RetCode retCode, Core c) {
		 double[] outFastK = new double[5000];
			double[] outFastD = new double[5000];
			MInteger outNBElement = new MInteger(); 
			MInteger outBegIdx = new MInteger(); 
			
			double lowk=0,lowd=0,highk=0,highd=0,volk = 0,vold=0,pricek = 0,priced=0,finalk = 0,finald = 0;
			/*double[] outMin = null;
			double[] outMax = null;
			retCode = c.minMax(0, lowdiffArray.length-1, lowdiffArray, 14, outBegIdx, outNBElement, outMin, outMax);
			*/
			retCode = c.stochF(0, lowdiffArray.length-1, lowdiffArray, lowdiffArray, lowdiffArray, 14, 3, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
			 if (retCode == RetCode.Success) {
				 try{
				 lowk=outFastK[outNBElement.value-1] ;
				 lowd=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 lowk=75;
					 lowd=75;
				 }
				// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
			 
			 retCode = c.stochF(0, highdiffArray.length-1, highdiffArray, highdiffArray, highdiffArray, 14, 3, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
			 if (retCode == RetCode.Success) {
				 try{
				 highk= outFastK[outNBElement.value-1] ;
				 highd=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 highk=75;
					 highd=75;
				 }
				 //System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
			 
			 retCode = c.stochF(0, obvArray.length-1, obvArray, obvArray, obvArray, 14, 3, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
		
			 if (retCode == RetCode.Success) {
				 try
				 {
				 volk=outFastK[outNBElement.value-1] ;
				 vold=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 volk=75;
					 vold=75;
				 }
			//	System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
			 
			 retCode = c.stochF(0, lowArray.length-1, lowArray, lowArray, lowArray, 14, 5, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
				
			 if (retCode == RetCode.Success) {
				 try
				 {
				pricek=outFastK[outNBElement.value-1];
				priced=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 pricek=75;
					 priced=75;
				 }
				// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
		
			 finalk = (volk+pricek)/2;
			 finald= (vold+((lowk+lowd+highk+highd+pricek+priced)/6))/2;
			 
			 
			 return (new double[]{finalk,finald});
			 
			 
			 
	}

}
