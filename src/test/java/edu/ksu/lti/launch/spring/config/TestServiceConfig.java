package edu.ksu.lti.launch.spring.config;

import edu.ksu.canvas.CanvasApiFactory;
import run.mycode.lti.launch.service.ConfigService;
import run.mycode.lti.launch.service.LtiLaunchKeyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestServiceConfig {

    @Bean
    public ConfigService fakeConfigService() {
        return key -> {
            switch (key) {
                case "canvas_url" :
                    return "someDomain";
                default:
                    return "";
            }
        };
    }

    @Bean
    public LtiLaunchKeyService fakeLtiLaunchKeyService() {
        return new LtiLaunchKeyService() {
            @Override
            public String findSecretForKey(String key) {
                return null;
            }
        };
    }

    @Bean
    public CanvasApiFactory canvasApiFactory() {
        return new CanvasApiFactory("");
    }

}
