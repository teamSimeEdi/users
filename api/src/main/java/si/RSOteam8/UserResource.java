package si.RSOteam8;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResource {

    @GET
    public Response getAllUsers() {
        List<User> users = new LinkedList<User>();
        User user = new User();
        user.setId("1");
        user.setUsername("janeznovak");
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
