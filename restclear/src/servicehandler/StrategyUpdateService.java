package servicehandler;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import holderobjects.StrategyThreadExecutor;
import holderobjects.ThreadExecutorInstance;
import servicehandlercore.Alphavdatadownloader;
import servicehandlercore.StrategyUpdater;

@Path("/rest")

public class StrategyUpdateService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/StrategyUpdateService")
	public Response getMsg(@Context HttpServletRequest request,@QueryParam("code") String code) {
		if (!code.equals("MuttonBriyani"))
			return null;
		HashMap <String,String> setting = new HashMap <String,String> ();
	    setting = (HashMap<String, String>) request.getServletContext().getAttribute("LocalSetting");
     String output = "Get:Jersey say : " ;
     StrategyThreadExecutor tEI = StrategyThreadExecutor.getInstance();
     tEI.addThread(new StrategyUpdater(setting));
        return Response.status(200).entity(output).build();
    }
}
