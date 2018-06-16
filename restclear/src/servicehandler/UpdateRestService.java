package servicehandler;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import holderobjects.ThreadExecutorInstance;
import servicehandlercore.Alphavdatadownloader;


@Path("/rest")
public class UpdateRestService {

	//private final @Context HttpServletRequest httpServletRequest ;
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/UpdateService")
	public Response getMsg(@Context HttpServletRequest request,@QueryParam("code") String code) {
		if (!code.equals("MuttonBriyani"))
			return null;
		HashMap <String,String> setting = new HashMap <String,String> ();
	    setting = (HashMap<String, String>) request.getServletContext().getAttribute("LocalSetting");
     String output = "Get:Jersey say : " ;
     ThreadExecutorInstance tEI = ThreadExecutorInstance.getInstance();
     tEI.addThread(new Alphavdatadownloader(setting));
        return Response.status(200).entity(output).build();
    }
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/UpdateService")
	public Response getMsg1(@Context HttpServletRequest request,@QueryParam("code") String code) {
		if (!code.equals("MuttonBriyani"))
			return null;
		HashMap <String,String> setting = new HashMap <String,String> ();
	    setting = (HashMap<String, String>) request.getServletContext().getAttribute("LocalSetting");
     String output = "Get:Jersey say : " ;
     ThreadExecutorInstance tEI = ThreadExecutorInstance.getInstance();
     tEI.addThread(new Alphavdatadownloader(setting));
        return Response.status(200).entity(output).build();
    }
	
	
}
