package run.mycode.lti.launch.controller;

import run.mycode.lti.launch.model.LtiLaunchData;
import run.mycode.lti.launch.model.LtiSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to handle the initial launch of an LTI application and creating a
 * session with authentication information from Canvas in it. After the
 * LTI data's signature is verified it will forward the request to an 
 * "initial view" that all implementing classes must supply via the 
 * getInitialViewPath() method. This initial view method should verify
 * that there is a valid ltiSession with an eID in it and can then serve
 * up its content.
 */
public abstract class LtiLaunchController {
    private static final Logger LOG = LoggerFactory.getLogger(LtiLaunchController.class);

    @RequestMapping(value = "/launch", method = RequestMethod.POST)
    public String ltiLaunch(@ModelAttribute LtiLaunchData ltiData, HttpSession session) throws Exception {
        // Invalidate the session to clear out any old data
        session.invalidate();
        LOG.debug("launch!");
        String eID = ltiData.getUser_id();
        LtiSession ltiSession = new LtiSession();
        ltiSession.setApplicationName(getApplicationName());
        ltiSession.setInitialViewPath(getInitialViewPath());
        ltiSession.setEid(eID);
        ltiSession.setLtiLaunchData(ltiData);
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession newSession = sra.getRequest().getSession();
        newSession.setAttribute(LtiSession.class.getName(), ltiSession);
        LOG.info("launching LTI integration '" + getApplicationName() + " as user " + eID);
        LOG.debug("forwarding user to: " + getInitialViewPath());
        return "forward:" + getInitialViewPath();
    }

    /** return the initial path that the user should be sent
     *  to after authenticating the LTI launch request */
    protected abstract String getInitialViewPath();

    /** The identifier of this LTI application. Used to look up config
     * values in the database and such
     */
    protected abstract String getApplicationName();
}
