package pro.sky.telegrambot.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.configuration.NotificationConfiguration;
import pro.sky.telegrambot.controllers.TelegramBot;
import pro.sky.telegrambot.dto.notificationtask.NotificationTaskSaveDto;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;
import pro.sky.telegrambot.services.api.NotificationTaskServiceAPI;
import pro.sky.telegrambot.utils.MessageUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final NotificationTaskServiceAPI notificationTaskService;
    private final NotificationConfiguration notificationConfiguration;
    private final MessageUtils messageUtils;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskServiceAPI notificationTaskService, NotificationConfiguration notificationConfiguration, MessageUtils messageUtils) {
        this.telegramBot = telegramBot;
        this.notificationTaskService = notificationTaskService;
        this.notificationConfiguration = notificationConfiguration;
        this.messageUtils = messageUtils;
    }

    public void add(Update update) {
        notificationTaskService.save(parseUpdate(update));
    }

    private NotificationTaskSaveDto parseUpdate(Update update){
        var message = update.getMessage().getText();
        Pattern pattern = Pattern.compile(notificationConfiguration.getPattern());
        Matcher matcher = pattern.matcher(message);
        if (!matcher.find()) {
            throw new RuntimeException();
        }
        LocalDateTime dateTime = LocalDateTime.parse(
                matcher.group("date"),
                DateTimeFormatter.ofPattern(notificationConfiguration.getDateFormat())
        );
        return new NotificationTaskSaveDto(
                Long.valueOf(update.getMessage().getChatId().toString()),
                matcher.group("message"),
                dateTime
        );
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        List<NotificationTask> notifications = notificationTaskService.findAllNeedToSendNotificationsToNow();
        notifications.forEach(
                notificationTask -> {
                    try {
                        telegramBot.sendAnswerMessage(
                                messageUtils.generateSendMessageWithText(
                                        notificationTask.getChatId(),
                                        notificationTask.getMessage()
                                )
                        );
                        notificationTaskService.sent(notificationTask);
                    } catch (RuntimeException e) {
                        logger.error("Не удалось отправить сообщение с id = \"" + notificationTask.getId() + "\"");
                    }
                }
        );
    }

}
