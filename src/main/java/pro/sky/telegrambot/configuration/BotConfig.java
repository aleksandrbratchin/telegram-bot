package pro.sky.telegrambot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pro.sky.telegrambot.utils.YamlPropertySourceFactory;

@Configuration
@PropertySource(value = "classpath:telegram.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
