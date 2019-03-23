package run.mycode.lti.launch.spring.config;

import run.mycode.lti.launch.oauth.LtiConsumerDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.nonce.InMemoryNonceServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import run.mycode.lti.launch.oauth.LtiAuthenticationHandler;

/**
 * This configuration class sets up Spring Security to authenticate LTI
 * launch requests based on the OAuth signature present in the POST params.
 * It also sets up some common HTTP headers that get returned to the browser
 * on each request to make browsers happy running inside of an iframe.
 */
@Configuration
//@EnableWebMvcSecurity
public class LtiLaunchSecurityConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOG = Logger.getLogger(LtiLaunchSecurityConfig.class);

    @Configuration
    @Order(2)
    public static class LTISecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private LtiConsumerDetailsService consumerDetailsService;
        @Autowired
        private LtiAuthenticationHandler authenticationHandler;
        @Autowired
        private OAuthProviderTokenServices oauthProviderTokenServices;

        @Override
        public void configure(WebSecurity web) throws Exception {
            //security debugging should not be used in production!
            //You probably won't even want it in development most of the time but I'll leave it here for reference.
            /*
            if(LOG.isDebugEnabled()) {
                web.debug(true);
            }
            */
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            LOG.debug("configuring HttpSecurity");
            http.requestMatchers()
                .antMatchers("/lti/lt/**").and()
                .addFilterBefore(configureProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated().and()//.csrf().disable()
                .headers()
//                .addHeaderWriter(new XFrameOptionsHeaderWriter(new StaticAllowFromStrategy(new URI(canvasUrl))))
//                .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy",
//                        "default-src 'self' https://s.ksucloud.net https://*.instructure.com; " +
//                        "font-src 'self' https://s.ksucloud.net https://*.instructure.com; " +
//                        "script-src 'self' 'unsafe-inline' https://ajax.googleapis.com; " +
//                        "style-src 'self' 'unsafe-inline' https://*.instructure.com https://www.k-state.edu" ))
                .addHeaderWriter(new StaticHeadersWriter("P3P", "CP=\"This is just to make IE happy with cookies in this iframe\""));
        }

        private ProtectedResourceProcessingFilter configureProcessingFilter() {
            //Set up nonce service to prevent replay attacks.
            InMemoryNonceServices nonceService = new InMemoryNonceServices();
            nonceService.setValidityWindowSeconds(600);

            ProtectedResourceProcessingFilter processingFilter = new ProtectedResourceProcessingFilter();
            processingFilter.setAuthHandler(authenticationHandler);
            processingFilter.setConsumerDetailsService(consumerDetailsService);
            processingFilter.setNonceServices(nonceService);
            processingFilter.setTokenServices(oauthProviderTokenServices);
            return processingFilter;
        }
    }

    @Bean(name = "oauthProviderTokenServices")
    public OAuthProviderTokenServices oauthProviderTokenServices() {
        // NOTE: we don't use the OAuthProviderTokenServices for 0-legged but it cannot be null
        return new InMemoryProviderTokenServices();
    }
}
