package si.RSOteam8;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@Readiness
@ApplicationScoped
public class UserHealthCheck implements HealthCheck {

    public HealthCheckResponse call() {
        try {
            return HealthCheckResponse.up(UserHealthCheck.class.getSimpleName());

        } catch (Exception exception){
            Logger.getLogger(UserHealthCheck.class.getSimpleName()).warning(exception.getMessage());
        }
        return HealthCheckResponse.down(UserHealthCheck.class.getSimpleName());
    }

}
