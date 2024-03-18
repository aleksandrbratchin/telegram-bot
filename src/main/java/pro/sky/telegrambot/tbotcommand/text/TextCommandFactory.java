package pro.sky.telegrambot.tbotcommand.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.configuration.NotificationConfiguration;

import java.util.regex.Pattern;

@Component
public class TextCommandFactory {

    private final NotificationConfiguration notificationConfiguration;
    private final NotificationCommand notificationCommand;
    private final StartCommand startCommand;
    private final TestCommand testCommand;
    private final UnknownCommand unknownCommand;

    public TextCommandFactory(NotificationConfiguration notificationConfiguration, NotificationCommand notificationCommand, StartCommand startCommand, TestCommand testCommand, UnknownCommand unknownCommand) {
        this.notificationConfiguration = notificationConfiguration;
        this.notificationCommand = notificationCommand;
        this.startCommand = startCommand;
        this.testCommand = testCommand;
        this.unknownCommand = unknownCommand;
    }

    public TextCommand getCommand(Update update) {
        String textMessage = update.getMessage().getText();
        if (textMessage.equals("/start")) {
            return startCommand;
        } else if (textMessage.equals("/test")) {
            return testCommand;
        } else if (Pattern.matches(notificationConfiguration.getPattern(), textMessage)) {
            return notificationCommand;
        }
        return unknownCommand;
    }

}
