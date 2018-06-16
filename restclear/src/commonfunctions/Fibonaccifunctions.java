package commonfunctions;

import java.util.ArrayList;

import org.hibernate.Session;

import hibernateobjects.Defaultcomplexsetting;
import hibernateobjects.Nsefibdata;
import holderobjects.TickData;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

public class Fibonaccifunctions extends Trendfunctions {

	public void updatenseFinonacci(ArrayList<TickData> tickhash, Session session, String stocksymbol, String duration,
			float[] highArray, float[] lowArray, float[] closeArray) {
		// TODO Auto-generated method stub
		
	        
	        
	        Core c1 = new Core();
	        double[] out = new double[5000];
	        MInteger begin = new MInteger();
	        MInteger length = new MInteger();
	        RetCode retCode = null;
	        Nsefibdata fd = new Nsefibdata();
	        fd = getfibondata(stocksymbol,closeArray,highArray,c1,retCode,begin,length,out,duration,session);
	        System.out.println("Before fibdata update");
	        session.saveOrUpdate(fd);
	        System.out.println("after fibdata updated");
	        
	        updatensetrend(tickhash,session,stocksymbol,duration,highArray,lowArray,closeArray);
	    	
	        
	}

	private Nsefibdata getfibondata(String stocksymbol, float[] closeArray, float[] highArray, Core c1, RetCode retCode, MInteger begin, MInteger length, double[] out, String duration, Session session) {
		// TODO Auto-generated method stub
		
		Defaultcomplexsetting st = new Defaultcomplexsetting();
		st.setSettingtype("FIB");
		st.setDuration(duration);
		
		Defaultcomplexsetting dcs = (Defaultcomplexsetting) session.get(Defaultcomplexsetting.class, st);
		
		int avg1=200,avg2=300,period=200;
		float factor=6f;
		
		if (dcs != null)
		{
			period = Integer.parseInt(dcs.getPeriod());
			avg1 = Integer.parseInt(dcs.getAvg1());
			avg2 = Integer.parseInt(dcs.getAvg2());
			factor = Float.valueOf(dcs.getFactor());
		}
		
		double lw = 0;
		double hg = 0;
        float dev =0;
        
        ArrayList<Double> bh1 = new ArrayList<Double> (),bh2 = new ArrayList<Double> (),bh3 = new ArrayList<Double> (),bh4 = new ArrayList<Double> (),bh = new ArrayList<Double> (),mid1 = new ArrayList<Double> (),mid2 = new ArrayList<Double> (),mid3 = new ArrayList<Double> (),mid4 = new ArrayList<Double> (),bl= new ArrayList<Double> (),bl1=new ArrayList<Double> (),bl2=new ArrayList<Double> ();
        float f_bh1;
        
float f_bh2,f_bh3,f_bh4,f_bh,f_mid1,f_mid2,f_mid3,f_mid4,f_bl,f_bl1,f_bl2;
       

for (int i=period ; i< closeArray.length;i++)
{
	hg = 0;
	lw = 0;
	
	retCode = c1.min(i-period, i, closeArray, period, begin, length, out);
	if (retCode == RetCode.Success) {
		lw =  out[length.value-1];
	}
	retCode = c1.max(i-period, i, highArray, period, begin, length, out);
	if (retCode == RetCode.Success) {
		hg = out[length.value-1];
	}
	dev = (float) (hg-lw);
	
	
double basish_h1 = (float) (hg + (0.236*dev));
bh1.add(basish_h1);
double basish_h2 = (float) (hg + (0.414*dev));
bh2.add(basish_h2);
double basish_h3 = (float) (hg + (0.618*dev));
bh3.add(basish_h3);
double basish_h4 = (float) (hg + (1.001*dev));
bh4.add(basish_h4);
double basish = hg;
bh.add(basish);
double mid_1 = (float) (hg - (0.382*dev));
mid1.add(mid_1);
double mid_2 = (float) (hg - (0.618*dev));
mid2.add(mid_2);
double mid_3 = (float) (hg - (0.764*dev));
mid3.add(mid_3);
double mid_4 = (float) (hg - (0.236*dev*factor));
mid4.add(mid_4);
double basisl = lw;
bl.add(basisl);
double basis_l1 = (float) (lw- (0.236*dev*factor));
bl1.add(basis_l1);
double basis_l2 = (float) (lw- (0.414*dev));
bl2.add(basis_l2);
	
 
}		
		
f_bh1 = getsma23(bh1,c1,begin, length, out,retCode,avg1,avg2);
f_bh2 = getsma23(bh2,c1,begin, length, out,retCode,avg1,avg2);
f_bh3 = getsma23(bh3,c1,begin, length, out,retCode,avg1,avg2);
f_bh4 = getsma23(bh4,c1,begin, length, out,retCode,avg1,avg2);
f_bh = getsma23(bh,c1,begin, length, out,retCode,avg1,avg2);
f_mid1 =  getsma23(mid1,c1,begin, length, out,retCode,avg1,avg2);
f_mid2 =  getsma23(mid2,c1,begin, length, out,retCode,avg1,avg2);
f_mid3 =  getsma23(mid3,c1,begin, length, out,retCode,avg1,avg2);
f_mid4 =  getsma23(mid4,c1,begin, length, out,retCode,avg1,avg2);
f_bl = getsma23(bl,c1,begin, length, out,retCode,avg1,avg2);
f_bl1 = getsma23(bl1,c1,begin, length, out,retCode,avg1,avg2);
f_bl2 = getsma23(bl2,c1,begin, length, out,retCode,avg1,avg2);

bh1=null;
bh2=null;
bh3=null;
bh4=null;
bh=null;
mid1=null;
mid2=null;
mid3=null;
mid4=null;
bl=null;
bl1=null;
bl2=null;

Nsefibdata fd = new Nsefibdata();		
fd.setStocksymbol(stocksymbol);	
fd.setDuration(duration);
fd.setBh(f_bh);
fd.setBh1(f_bh1);
fd.setBh2(f_bh2);
fd.setBh3(f_bh3);
fd.setBh4(f_bh4);
fd.setBl(f_bl);
fd.setBl1(f_bl1);
fd.setBl2(f_bl2);
fd.setMid1(f_mid1);
fd.setMid2(f_mid2);
fd.setMid3(f_mid3);
fd.setMid4(f_mid4);
	
		
		
		
		
return fd;
	}

	private float getsma23(ArrayList<Double> inarray, Core c1, MInteger begin, MInteger length, double[] out, RetCode retCode, int avg1, int avg2) {
		// TODO Auto-generated method stub
		
		 double[] closeArray = new double[inarray.size()];
	        int k = 0;

	        for (Double f : inarray) {
	        	closeArray[k++] = (f != null ? f : Double.NaN); // Or whatever default you want.
	        }
	        
		float sma200 =0.1f ,sma300 = 0.1f;
		retCode = c1.sma(0, closeArray.length-1, closeArray, avg1, begin, length, out);
		if (retCode == RetCode.Success) {
			if(length.value == 0)
				return 0;
			sma200 = (float) out[length.value-1];
		}
		retCode = c1.sma(0, closeArray.length-1, closeArray, avg2, begin, length, out);
		if (retCode == RetCode.Success) {
			if(length.value == 0)
				return sma200;
			sma300 = (float) out[length.value-1];
		}
		closeArray =null;
		return (sma200+sma300)/2;
		
		
	}


}
