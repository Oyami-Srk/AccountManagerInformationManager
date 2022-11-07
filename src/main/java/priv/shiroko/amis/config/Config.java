package priv.shiroko.amis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "amis")
public class Config {
    private String cacheFolder;
    private String uploadFolder;
    private int bcryptCost = 12;
    private Defaults defaults;

    @Setter
    @Getter
    public static class Defaults {
        private String password;
        private String adminUsername;
        private String adminPassword;
    }
}
