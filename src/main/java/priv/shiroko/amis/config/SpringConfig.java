package priv.shiroko.amis.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(Config.class)
public class SpringConfig {
    @Bean
    StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
}
