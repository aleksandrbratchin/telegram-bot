package pro.sky.telegrambot.tbotcommand.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.configuration.NotificationConfiguration;
import pro.sky.telegrambot.listener.UpdatesListener;
import pro.sky.telegrambot.utils.MessageUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
@Component
public class TestCommand implements TextCommand {
    private final MessageUtils messageUtils;
    private final UpdatesListener updatesListener;
    private final NotificationConfiguration notificationConfiguration;
    public TestCommand(MessageUtils messageUtils, UpdatesListener telegramBotUpdatesListener, NotificationConfiguration notificationConfiguration) {
        this.messageUtils = messageUtils;
        this.updatesListener = telegramBotUpdatesListener;
        this.notificationConfiguration = notificationConfiguration;
    }

    @Override
    public SendMessage process(Update update) {
        LocalTime timeNow = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(
                        timeNow.getHour(),
                        timeNow.getMinute() + 1,
                        0
                )
        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(notificationConfiguration.getDateFormat());
        String text = dateTime.format(formatter) + " Тестовое напоминание";
        update.getMessage().setText(text);
        updatesListener.add(update);
        return messageUtils.generateSendMessageWithText(
                update,
                "Создано тестовое напоминание: \"" + text + "\""
        );
    }
}
