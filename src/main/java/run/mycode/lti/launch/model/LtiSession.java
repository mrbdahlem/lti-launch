package run.mycode.lti.launch.model;

import org.springframework.stereotype.Component;
import run.mycode.lti.launch.controller.LtiLaunchController;

/**
 * Class to hold LTI session data. It is created and populated when the LTI application is first
 * launched and then stored in the session for future reference. Some commonly accessed information
 * is stored in their own variables (like eID) but all data we get as part of the LTI launch request
 * is stored in the {@link LtiLaunchData} object if you need it.
 *
 * Ideally this would be a session scoped bean that gets autowired into the controllers.
 * Unfortunately this breaks when I try to wire it into both an LTI controller and the Oauth controller
 * so I ended up making my own session management in {@link LtiLaunchController}
 */
@Component
public class LtiSession {

    private String applicationName;
    private String initialViewPath;
    private String eid;
    private LtiLaunchData ltiLaunchData;


    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setInitialViewPath(String initialViewPath) {
        this.initialViewPath = initialViewPath;
    }

    public String getInitialViewPath() {
        return initialViewPath;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEid() {
        return eid;
    }

    public void setLtiLaunchData(LtiLaunchData ltiLaunchData) {
        this.ltiLaunchData = ltiLaunchData;
    }

    public LtiLaunchData getLtiLaunchData() {
        return ltiLaunchData;
    }
}
