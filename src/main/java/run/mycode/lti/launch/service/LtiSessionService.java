package run.mycode.lti.launch.service;

import run.mycode.lti.launch.exception.NoLtiSessionException;
import run.mycode.lti.launch.model.LtiSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import run.mycode.lti.launch.model.LtiLaunchData;

/**
 * Created by alexanda on 7/27/16.
 */
@Service
@Scope("session")
public class LtiSessionService {
    private static final Logger LOG = LoggerFactory.getLogger(LtiSessionService.class);

    /**
     * Get the LtiSession object from the HTTP session. It is put there up in the ltiLaunch method.
     * This should really be done using a SpringSecurityContext to get the authenticated principal
     * which could then hold this LTI information. But I'm having serious trouble figuring out how to
     * do this correctly and I need *some* kind of session management for right now.
     * Another approach would be to create this as a session scoped bean but the problem there is that
     * I need to share this session object across controllers (the OauthController to be specific) and
     * this breaks for some reason so I'm rolling my own session management here.
     *
     * @return The current user's LTI session information
     * @throws run.mycode.lti.launch.exception.NoLtiSessionException if the user does not have a valid LTI session.
     */
    public LtiSession getLtiSession() throws NoLtiSessionException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = sra.getRequest();
        HttpSession session = req.getSession();
        LtiSession ltiSession = (LtiSession) session.getAttribute(LtiSession.class.getName());
        if (ltiSession == null) {
            throw new NoLtiSessionException();
        }
        return ltiSession;
    }
        
    /**
     * Create a new Lti session for the request
     * 
     * @param ltiData the LTI parameters sent with the request
     * @param request the current request
     * 
     * @return the LtiSession data 
     */
    public LtiSession buildLtiSession(LtiLaunchData ltiData, 
                                      HttpServletRequest request) {
               
        HttpSession session = request.getSession();
        
        session.invalidate();
        
        String eID = ltiData.getUser_id();
        LtiSession newLtiSession = new LtiSession();
        newLtiSession.setEid(eID);
        newLtiSession.setLtiLaunchData(ltiData);
        
        session = request.getSession(true);
        session.setAttribute(LtiSession.class.getName(), newLtiSession);
                
        LOG.info("launching LTI integration as user " + eID);
        
        return newLtiSession;
    }    
}
