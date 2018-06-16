package servicehandler;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import dataobjects.StrategyinvestResult;
import springbeans.InvestmentStrategy1;

@Path("/rest")

public class InvestTrendHandlerService {

	@GET
	@Produces("application/json")
	@Path("/InvestTrendHandlerService")
	public ArrayList<StrategyinvestResult> getMsg1(@Context HttpServletRequest request,@QueryParam("diff") String diff,
			@QueryParam("code") String code) {
		
		if (!code.equals("MuttonBriyani"))
			return null;
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-bean.xml");
	      InvestmentStrategy1 processor = (InvestmentStrategy1) context.getBean("invstrategy1");
	      
	      
     String output = "Get:Jersey say : " ;
     
     //call invest handler return data and send it as json
        return processor.result(diff);
    }
	
}
