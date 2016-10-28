package by.danceform.app.config;

import by.danceform.app.aop.logging.DevelopmentLoggingAspect;
import by.danceform.app.aop.logging.ProductionLoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(Constants.SPRING_PROFILE_PRODUCTION)
    public ProductionLoggingAspect productionLoggingAspect() {
        return new ProductionLoggingAspect();
    }

    @Bean
    @Profile({ Constants.SPRING_PROFILE_DEVELOPMENT, Constants.SPRING_PROFILE_SWAGGER })
    public DevelopmentLoggingAspect developmentLoggingAspect() {
        return new DevelopmentLoggingAspect();
    }
}
