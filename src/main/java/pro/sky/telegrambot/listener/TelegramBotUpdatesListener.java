package pro.sky.telegrambot.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;
import pro.sky.telegrambot.utils.MessageUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final String PATTERN = "^(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}) (.+)$";
    private TelegramBot telegramBot;

    private final NotificationTaskRepository notificationTaskRepository;
    private final MessageUtils messageUtils;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository, MessageUtils messageUtils) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.messageUtils = messageUtils;
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void add(Update update) {
        var message = update.getMessage().getText();
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(message);
        if (!matcher.find()) {
            throw new RuntimeException();
        }
        LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        NotificationTask notificationTask = new NotificationTask(
                Long.valueOf(update.getMessage().getChatId().toString()),
                matcher.group(2),
                dateTime
        );
        notificationTaskRepository.save(notificationTask);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        List<NotificationTask> notifications = notificationTaskRepository.findAllByNotificationSentFalseAndDateNotificationLessThanEqual(
                LocalDateTime.now()
        );
        notifications.forEach(
                notificationTask -> {
                    try {
                        telegramBot.sendAnswerMessage(
                                messageUtils.generateSendMessageWithText(
                                        notificationTask.getChatId(),
                                        notificationTask.getMessage()
                                )
                        );
                        notificationTask.setNotificationSent(true);
                        notificationTaskRepository.save(notificationTask);
                    } catch (RuntimeException e) {
                        logger.error(LocalDateTime.now() + ": Не удалось отправить сообщение с id = \"" + notificationTask.getId() + "\"");
                    }

                }
        );

    }

}
