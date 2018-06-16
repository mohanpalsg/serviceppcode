package springbeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dataobjects.InvestStratone;
import dataobjects.StrategyinvestResult;



public class InvestmentStrategy1 {

	private SessionFactory factory; 
	//private Session s1;
	public InvestmentStrategy1(boolean flag)
	{
		if (flag)
		{
			 this.factory =new Configuration().configure().buildSessionFactory();
		}
	}
	public ArrayList<StrategyinvestResult> result(String diff) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		 Session s1 = this.factory.openSession();
		
		
		List<InvestStratone> list = s1.createQuery("FROM InvestStratone where trendup > 0 and ((ltp-trendup)/trendup)*100 < "+Double.parseDouble(diff)).list();
		s1.close();
		this.factory.close();
		
		ArrayList <StrategyinvestResult> result= new ArrayList <StrategyinvestResult>();
		Iterator it = list.iterator();
		while (it.hasNext())
		{ 
			InvestStratone inv = (InvestStratone)it.next();
			StrategyinvestResult st = new StrategyinvestResult();
			st.setStocksymbol(inv.getStocksymbol());
			st.setLtp(Double.parseDouble(formatter.format(inv.getLtp())));
			st.setSupport(inv.getTrendup());
			st.setResistance(inv.getTrenddown());
			st.setTrendstatus(inv.getTrendstatus());
			st.setDiffpercent(Double.parseDouble(formatter.format(((inv.getLtp()-inv.getTrendup())/inv.getTrendup())*100)));
			st.setPotential(Double.parseDouble(formatter.format(((inv.getTrenddown()-inv.getLtp())/inv.getLtp())*100)));
			result.add(st);
			
	}
		

	return result;
}
	public void cleanup()
	{
		//this.factory.close();
	}
}