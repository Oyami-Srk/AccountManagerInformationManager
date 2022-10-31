package priv.shiroko.amis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix="amis")
public class Config {
    private int bcryptCost = 12;
    private Defaults defaults;

    @Setter
    @Getter
    public static class Defaults {
        private String adminUsername;
        private String adminPassword;
    }
}
