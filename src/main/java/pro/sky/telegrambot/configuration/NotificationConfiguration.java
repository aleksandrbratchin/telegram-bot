package pro.sky.telegrambot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pro.sky.telegrambot.utils.YamlPropertySourceFactory;

@Configuration
/*@PropertySource(value = "classpath:notification.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "notification")*/
public class NotificationConfiguration {
    @Value("${notification.pattern}")
    private String pattern;
    @Value("${notification.format.date}")
    private String dateFormat;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

}
