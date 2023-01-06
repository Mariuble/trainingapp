package treningsapp.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import treningsapp.core.UserHandler;

@Path(TreningsAppService.TreningsAppPath)

public class TreningsAppService {

  public static final String TreningsAppPath = "treningsapp";

  //private static final Logger LOG = LoggerFactory.getLogger(TodoListResource.class);
  

  @Inject 
  private UserHandler userHandler;
  
  @GET
  @Path("test")
  public String getTest() {
    return "getUserHandler";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public UserHandler getUserHandler() {
    return this.userHandler;
  }

  @Path("/{username}")
  public TreningsAppResource getUser(@PathParam("username") String username) {
    return new TreningsAppResource(this.userHandler, username);
  }
}