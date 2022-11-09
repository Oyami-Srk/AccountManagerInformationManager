package priv.shiroko.amis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.shiroko.amis.interceptor.AuthInterceptor;
import priv.shiroko.amis.interceptor.PermissionInterceptor;

@Configuration
public class Interceptors implements WebMvcConfigurer {
    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // register is manually done by admin
        registry.addInterceptor(authInterceptor())
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/login.html")
                .excludePathPatterns("/css/*")
                .excludePathPatterns("/editor/*")
                .excludePathPatterns("/images/*")
                .excludePathPatterns("/js/*")
                .excludePathPatterns("/src/*")
        ;
        registry.addInterceptor(permissionInterceptor())
                .addPathPatterns("/api/user/set_status")
                .addPathPatterns("/api/user/reset_password")
                .addPathPatterns("/api/**/add")
                .addPathPatterns("/api/**/update")
                .addPathPatterns("/api/**/delete")
        ;
    }
}
