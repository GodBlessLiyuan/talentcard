package com.talentcard.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    public SmsConfig(){

    }

    @Configuration
    @AutoConfigureBefore({AdminServerNotifierAutoConfiguration.NotifierTriggerConfiguration.class, AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration.class})
    public static class CustomNotifierConfiguration {
        public CustomNotifierConfiguration() {

        }

        @Bean
        @ConditionalOnMissingBean
        @ConfigurationProperties("spring.boot.admin.notify.telegram")
        public CustomNotifier customNotifierNotifier(InstanceRepository repository) {
            return new CustomNotifier(repository);
        }
    }

}
