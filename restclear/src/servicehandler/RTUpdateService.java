package servicehandler;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import holderobjects.RealtimeUpdateThreadExecutor;
import holderobjects.StrategyThreadExecutor;
import servicehandlercore.Alphavdatadownloader;
import servicehandlercore.Traderspitdownloader;

@Path("/rest")

public class RTUpdateService {



	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/RTUpdateService")
	public Response getMsg1(@Context HttpServletRequest request,@QueryParam("code") String code) {
		if (!code.equals("MuttonBriyani"))
			return null;
     String output = "Get:Jersey say : " ;
     RealtimeUpdateThreadExecutor tEI = RealtimeUpdateThreadExecutor.getInstance();
     tEI.addThread(new Traderspitdownloader());
        return Response.status(200).entity(output).build();
    }
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/RTUpdateService")
	public Response getMsg(@Context HttpServletRequest request,@QueryParam("code") String code) {
		if (!code.equals("MuttonBriyani"))
			return null;
     String output = "Get:Jersey say : " ;
     RealtimeUpdateThreadExecutor tEI = RealtimeUpdateThreadExecutor.getInstance();
     tEI.addThread(new Traderspitdownloader());
        return Response.status(200).entity(output).build();
    }

	
}
