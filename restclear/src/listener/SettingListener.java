package listener;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import hibernateobjects.WsAuthenticationobject;
import javassist.bytecode.Descriptor.Iterator;

/**
 * Application Lifecycle Listener implementation class SettingListener
 *
 */
@WebListener
public class SettingListener extends HttpServlet implements ServletContextListener {

   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap <String,String> durationMap = new HashMap<String,String>();
	private SessionFactory factory;
	/**
     * Default constructor. 
     */
    public SettingListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         HashMap <String,String> res = new HashMap<String,String>();
         
        durationMap.put("5min", "TIME_SERIES_INTRADAY");
     	durationMap.put("15min", "TIME_SERIES_INTRADAY");
     	durationMap.put("30min", "TIME_SERIES_INTRADAY");
     	durationMap.put("60min", "TIME_SERIES_INTRADAY");
     	durationMap.put("Daily", "TIME_SERIES_DAILY_ADJUSTED");
     	durationMap.put("Weekly", "TIME_SERIES_WEEKLY_ADJUSTED");
     	durationMap.put("Monthly", "TIME_SERIES_MONTHLY_ADJUSTED");
     	
     	try {
	         this.factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		
     	
		Session session = this.factory.openSession();
	
	
   	Transaction tx  = session.beginTransaction();
   	
	
	
	WsAuthenticationobject result = (WsAuthenticationobject) session.get(WsAuthenticationobject.class,"AlphaKey");
	
	res.put("AlphaKey", result.getKeyvalue());
	
	result = (WsAuthenticationobject) session.get(WsAuthenticationobject.class,"WhoAmI");
	res.put("WhoAmI",result.getKeyvalue());
	res.put("Functionparam",durationMap.get(result.getKeyvalue()));
	result = (WsAuthenticationobject) session.get(WsAuthenticationobject.class,"Load");
	res.put("Load", result.getKeyvalue());
	result = (WsAuthenticationobject) session.get(WsAuthenticationobject.class,"loadTdiff");
	res.put("loadtimediff", result.getKeyvalue());
	
	ServletContext ctx = arg0.getServletContext();
	ctx.setAttribute("LocalSetting", res);
	//this.getServletConfig().getServletContext().setAttribute("LocalSetting", res);
	tx.commit();
	session.close();
	this.factory.close();
	
	
	
	
    }
	
}
