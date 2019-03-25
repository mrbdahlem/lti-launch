package run.mycode.lti.launch.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.stereotype.Component;

import run.mycode.lti.launch.service.LtiLaunchKeyService;

/**
 * Consumer Details implementation. Given a consumer key, it looks up the secret
 * from a supplied LTI key lookup service and constructs a ConsumerDetails object
 * for Spring to work with to verify the authenticity of the LTI launch request.
 */
@Component
public class LtiConsumerDetailsService implements ConsumerDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(LtiConsumerDetailsService.class);

    private final LtiLaunchKeyService ltiKeyService;

    @Autowired
    public LtiConsumerDetailsService(LtiLaunchKeyService ltiKeyService) {
        this.ltiKeyService = ltiKeyService;
    }

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
        if(consumerKey.trim().isEmpty()) {
            throw new OAuthException("Supplied LTI key can not be blank");
        }
        String ltiKeySecret = ltiKeyService.findSecretForKey(consumerKey);
        if(ltiKeySecret.trim().isEmpty()) {
            throw new OAuthException("No secret found for LTI key " + consumerKey);
        }

        BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(consumerKey);
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(ltiKeySecret));
        consumerDetails.setConsumerName("");
        consumerDetails.setRequiredToObtainAuthenticatedToken(false);
        LOG.info("Constructed consumer details for LTI key " + consumerKey);
        return consumerDetails;
    }
}
