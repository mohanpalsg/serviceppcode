package commonfunctions;

import java.util.ArrayList;

import org.hibernate.Session;

import hibernateobjects.Pivot;
import holderobjects.TickData;

public class Pivotfunctions extends Basefunctions {


	

	protected void updatepivot(ArrayList<TickData> tickhash, Session session, String stocksymbol, String durationtype) {
		// TODO Auto-generated method stub
		
		TickData td = null;
		if (tickhash == null)
			return;
		if (!durationtype.equals("YEAR"))
		{
			if (tickhash.size() <2)
				return;
			td = tickhash.get(tickhash.size()-2);
		}
		else
		{
			if (tickhash.size() <1)
				return;
			td = tickhash.get(tickhash.size()-1);
		}
		updateCamarillaPivot(td,session,stocksymbol,durationtype,"CAMARILLA");
		updateFibonacciPivot(td,session,stocksymbol,durationtype,"FIBONACCI");
		updateClassicPivot(td,session,stocksymbol,durationtype,"CLASSIC");
		updateWoodiePivot(td,session,stocksymbol,durationtype,"WOODIE");
		updateDemarkPivot(td,session,stocksymbol,durationtype,"DEMARK");
	}

	private void updateDemarkPivot(TickData td, Session session, String stocksymbol, String durationtype,
			String pvtype) {
		// TODO Auto-generated method stub
		Pivot pv = new Pivot();
		pv.setDuration(durationtype);
		pv.setPivottype(pvtype);
		pv.setStocksymbol(stocksymbol);
		
		float pp,s1,s2,s3,s4,r1,r2,r3,r4;
		float x=0;
		if(td.getCloseprice() < td.getOpenprice())
		{
			x= (td.getHighprice()+(2*td.getLowprice())+td.getCloseprice());
		}
		else if (td.getCloseprice() > td.getOpenprice())
		{
			x= (td.getLowprice()+(2*td.getHighprice())+td.getCloseprice());
		}
		else
		{
			x= (td.getLowprice()+(2*td.getCloseprice())+td.getHighprice());
		}
		
		r1= (x/2)-td.getLowprice();
		s1= (x/2)-td.getHighprice();
		pp= (x/4);
		
		pv.setR1(r1);
		pv.setS1(s1);
		pv.setPp(pp);
		System.out.println("before demark pivot update");
		session.saveOrUpdate(pv);
		System.out.println("after demark pivot update");
	}

	private void updateWoodiePivot(TickData td, Session session, String stocksymbol, String durationtype,
			String pvtype) {
		// TODO Auto-generated method stub
		
	}

	private void updateClassicPivot(TickData td, Session session, String stocksymbol, String durationtype,
			String pvtype) {
		// TODO Auto-generated method stub
		
	}

	private void updateFibonacciPivot(TickData td, Session session, String stocksymbol, String durationtype,
			String pvtype) {
		// TODO Auto-generated method stub
		
	}

	private void updateCamarillaPivot(TickData td, Session session, String stocksymbol, String durationtype,
			String pvtype) {
		Pivot pv = new Pivot();
		pv.setDuration(durationtype);
		pv.setPivottype(pvtype);
		pv.setStocksymbol(stocksymbol);
		
		float pp,s1,s2,s3,s4,r1,r2,r3,r4;
		float range =0;
		/*
		 * R4 = C + RANGE * 1.1/2
R3 = C + RANGE * 1.1/4
R2 = C + RANGE * 1.1/6
R1 = C + RANGE * 1.1/12
PP = (HIGH + LOW + CLOSE) / 3
S1 = C - RANGE * 1.1/12
S2 = C - RANGE * 1.1/6
S3 = C - RANGE * 1.1/4
S4 = C - RANGE * 1.1/2 
		 */
		range = td.getHighprice()-td.getLowprice();
		r4= (float) (td.getCloseprice()+ (range*1.1)/2);
		r3= (float) (td.getCloseprice()+ (range*1.1)/4);
		r2= (float) (td.getCloseprice()+ (range*1.1)/6);
		r1= (float) (td.getCloseprice()+ (range*1.1)/12);
		pp = (float) (td.getCloseprice()+td.getHighprice()+td.getLowprice())/3;
		s1 = (float) (td.getCloseprice()- (range*1.1)/12);
		s2 = (float) (td.getCloseprice()- (range*1.1)/6);
		s3 = (float) (td.getCloseprice()- (range*1.1)/4);
		s4 = (float) (td.getCloseprice()- (range*1.1)/2);
		
		pv.setPp(pp);
		pv.setS1(s1);
		pv.setS2(s2);
		pv.setS3(s3);
		pv.setS4(s4);
		pv.setR1(r1);
		pv.setR2(r2);
		pv.setR3(r3);
		pv.setR4(r4);
		System.out.println("Before camariila update");
		session.saveOrUpdate(pv);
		System.out.println("after camarilla update");
	}



	

	

}
