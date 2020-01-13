package si.RSOteam8;


import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;


import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
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
    @RestClient
    private LeaderboardService leaderboardService;

    @Inject
    private ConfigProperties cfg;

    @GET
    public Response getAllUsers() {
        List<User> users = new LinkedList<User>();

        try (
                Connection conn = DriverManager.getConnection(cfg.getDburl(), cfg.getDbuser(), cfg.getDbpass());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users.users");
        ) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString(1));
                user.setUsername(rs.getString(2));
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println(e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(users).build();
    }
    /*@Counted(name = "getAllUsers-count")
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
    }*/

    @GET
    @Path("{userId}")
    public Response getUser(@PathParam("userId") String username) {

        try (
                Connection conn = DriverManager.getConnection(cfg.getDburl(), cfg.getDbuser(), cfg.getDbpass());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users.users WHERE \"user\" = " + "'"+username+"'");
        ) {
            if (rs.next()){
                User user = new User();
                user.setId(rs.getString(1));
                user.setUsername(rs.getString(2));
                return Response.ok(user).build();

            }
        }
        catch (SQLException e) {
            System.err.println(e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

   /* @POST
    public Response addNewUser(User user) {
        //Database.addCustomer(customer);
        return Response.noContent().build();
    }*/
    @POST
    public Response addNewUser(User userObj) {
        try (
                Connection conn = DriverManager.getConnection(cfg.getDburl(), cfg.getDbuser(), cfg.getDbpass());
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate("INSERT INTO users.users (\"user\") VALUES ('" + userObj.getUsername() + "')",
                    Statement.RETURN_GENERATED_KEYS);
            leaderboardService.addUserToLeaderboard(userObj.getUsername(),0);
        }
        catch (SQLException e) {
            System.err.println(e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.noContent().build();
    }
    @DELETE
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") String username) {
        try (
                Connection conn = DriverManager.getConnection(cfg.getDburl(), cfg.getDbuser(), cfg.getDbpass());
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate("DELETE FROM users.users WHERE \"user\" = " + "'"+username+"'");
            leaderboardService.deleteUserFromLeaderboard(username);
        }
        catch (SQLException e) {
            System.err.println(e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.noContent().build();
    }
    /*@DELETE
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") String userId) {
        //Database.deleteCustomer(customerId);
        return Response.noContent().build();
    }*/
}
