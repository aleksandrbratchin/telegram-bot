package pro.sky.telegrambot.controllers;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.tbotcommand.text.TextCommand;
import pro.sky.telegrambot.tbotcommand.text.TextCommandFactory;
import pro.sky.telegrambot.utils.MessageUtils;

@Controller
public class UpdateController {
    private final TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final TextCommandFactory textCommandFactory;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public UpdateController(TelegramBot telegramBot, MessageUtils messageUtils, TextCommandFactory textCommandFactory) {
        this.telegramBot = telegramBot;
        this.messageUtils = messageUtils;
        this.textCommandFactory = textCommandFactory;
    }

    @PostConstruct
    private void init() {
        telegramBot.register(this);
    }

    public void processUpdate(Update update) {
        if (update == null) {
            logger.error("Error: update");
            return;
        }
        if (!update.hasMessage()) {
            logger.error("Error update.getMessage()");
            return;
        }
        distributeMessageByType(update);
    }

    //распределить сообщение по типу
    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            try {
                logger.info("Пришло сообщение из чата: \"" + update.getMessage().getChatId() + "\" с текстом: \"" + message + "\"");
                TextCommand textCommand = textCommandFactory.getCommand(update);
                SendMessage answerMessage = textCommand.process(update);
                telegramBot.sendAnswerMessage(answerMessage);
                logger.info("Обработано сообщение из чата: \"" + update.getMessage().getChatId() + "\" с текстом: \"" + message + "\"");
            } catch (RuntimeException e) {
                logger.error("Произошла непредвиденная ошибка: " + e.getMessage());
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithText(update, "Что-то пошло не так!"));
            }
        } else {
            String msq = "Неизвестный тип сообщения!";
            logger.error(msq);
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithText(update, msq));
        }
    }

}
