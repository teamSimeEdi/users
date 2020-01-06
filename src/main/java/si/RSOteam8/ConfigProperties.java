package si.RSOteam8;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
@ConfigBundle("config-properties")
public class ConfigProperties {

    @ConfigValue(value = "test", watch = true)
    private String test;

    public String getTest(){
        return test;
    }
    public void setTest(String test){
        this.test = test;
    }
}
