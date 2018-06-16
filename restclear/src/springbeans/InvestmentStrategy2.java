package springbeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dataobjects.InvestStratTwo;
import dataobjects.InvestStratone;

import dataobjects.StrategyinvestResult;

public class InvestmentStrategy2 {
	private SessionFactory factory; 
	
	public InvestmentStrategy2(boolean flag)
	{
		if (flag)
		{
			 this.factory =new Configuration().configure().buildSessionFactory();
		}
	}
	
	public ArrayList<StrategyinvestResult> result(String diff) {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("#0.00");
		 Session s1 = this.factory.openSession();
		
		
		List<InvestStratTwo> list = s1.createQuery("FROM InvestStratTwo where lowband > 0 and ((ltp-lowband)/lowband)*100 < "+Double.parseDouble(diff)).list();
		s1.close();
		this.factory.close();
		
		ArrayList <StrategyinvestResult> result= new ArrayList <StrategyinvestResult>();
		Iterator it = list.iterator();
		while (it.hasNext())
		{ 
			InvestStratTwo inv = (InvestStratTwo)it.next();
			StrategyinvestResult st = new StrategyinvestResult();
			st.setStocksymbol(inv.getStocksymbol());
			st.setLtp(Double.parseDouble(formatter.format(inv.getLtp())));
			st.setSupport(inv.getLowband());
			st.setResistance(inv.getHighband());
			st.setTrendstatus(inv.getTrendstatus());
			st.setDiffpercent(Double.parseDouble(formatter.format(((inv.getLtp()-inv.getLowband())/inv.getLowband())*100)));
			st.setPotential(Double.parseDouble(formatter.format(((inv.getHighband()-inv.getLtp())/inv.getLtp())*100)));
			result.add(st);
			
	}
		

	return result;
	}

}
