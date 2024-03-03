package pro.sky.telegrambot.listener;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.telegrambot.configuration.BotConfig;
import pro.sky.telegrambot.utils.MessageUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final UpdatesListener updatesListener;
    private final MessageUtils messageUtils;

    public TelegramBot(BotConfig config, UpdatesListener telegramBotUpdatesListener, MessageUtils messageUtils) {
        super(config.getToken());

        this.config = config;
        this.updatesListener = telegramBotUpdatesListener;

        // Создание меню
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "Начальное меню"));
        commandList.add(new BotCommand("/test", "Создать тестовое напомининие"));
        /*commandList.add(new BotCommand("/new_game", "Cоздать новую игру"));
        commandList.add(new BotCommand("/find_game", "Найти игру"));*/
        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        this.messageUtils = messageUtils;
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @PostConstruct
    private void init() {
        updatesListener.registerBot(this);
    }

    /***
     * Сюда падают все сообщения
     */
    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            String textMessage = message.getText();
            try {
                if (textMessage.equals("/start")) {
                    sendAnswerMessage(messageUtils.generateSendMessageWithText(update, "Добро пожаловать!"));
                } else if (textMessage.equals("/test")) {
                    LocalTime timeNow = LocalTime.now();
                    LocalDateTime dateTime = LocalDateTime.of(
                            LocalDate.now(),
                            LocalTime.of(
                                    timeNow.getHour(),
                                    timeNow.getMinute() + 1,
                                    0
                            )
                    );
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    String text = dateTime.format(formatter) + " Тестовое напоминание";
                    sendAnswerMessage(
                            messageUtils.generateSendMessageWithText(
                                    update,
                                    "Создано тестовое напомининие \"" + text + "\""
                            )
                    );
                    update.getMessage().setText(text);
                    updatesListener.add(update);
                } else if (Pattern.matches(updatesListener.getPattern(), textMessage)) {
                    updatesListener.add(update);
                } else {
                    sendAnswerMessage(messageUtils.generateSendMessageWithText(update, "Не удалось распознать команду"));
                }
            } catch (Exception e) {
                sendAnswerMessage(messageUtils.generateSendMessageWithText(update, e.getMessage()));
            }
        } else {
            sendAnswerMessage(messageUtils.generateSendMessageWithText(update, "Неизвестный тип сообщения"));
        }
    }

    /***
     * Отправить ответ в телегу
     */
    public void sendAnswerMessage(SendMessage sendMessage) {
        if (sendMessage != null) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
