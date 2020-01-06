package si.RSOteam8;


import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;


import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
@Log
public class UserResource {
    

    @Inject
    private ConfigProperties cfg;

    @Counted(name = "getAllUsers-count")
    @GET
    public Response getAllUsers() {
        Logger.getLogger(UserHealthCheck.class.getSimpleName()).info("just testing");
        List<User> users = new LinkedList<User>();
        User user = new User();
        user.setId("1");
        user.setUsername(cfg.getTest());
        users.add(user);
        user = new User();
        user.setId("2");
        user.setUsername("peterklepec");
        users.add(user);
        return Response.ok(users).build();
    }

    @GET
    @Path("{userId}")
    public Response getUser(@PathParam("userId") String userId) {
        //Customer customer = Database.getCustomer(customerId);
        //return customer != null
        //       ? Response.ok(customer).build()
        //        : Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addNewUser(User user) {
        //Database.addCustomer(customer);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") String userId) {
        //Database.deleteCustomer(customerId);
        return Response.noContent().build();
    }
}
