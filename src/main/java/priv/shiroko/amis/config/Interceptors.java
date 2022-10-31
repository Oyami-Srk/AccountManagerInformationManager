package priv.shiroko.amis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.shiroko.amis.interceptor.AuthInterceptor;

@Configuration
public class Interceptors implements WebMvcConfigurer {
    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // register is manually done by admin
        registry.addInterceptor(authInterceptor())
                .excludePathPatterns("/api/login");
    }
}
