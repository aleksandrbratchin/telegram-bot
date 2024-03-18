package pro.sky.telegrambot.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BotConfigTest {

    @Autowired
    private BotConfig botConfig;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        assertThat(botConfig.getName()).isNotBlank();
        assertThat(botConfig.getToken()).isNotBlank();
    }

}