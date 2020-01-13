package si.RSOteam8;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.enterprise.context.Dependent;
@Path("v1/leaderboards")
@RegisterRestClient(configKey="leaderboards")
@Dependent
public interface LeaderboardService {

    @GET
    @Path("add")
    Response addUserToLeaderboard(@QueryParam("userId") String username, @QueryParam("score") int score);

    @DELETE
    Response deleteUserFromLeaderboard(@QueryParam("userId") String username);

}
