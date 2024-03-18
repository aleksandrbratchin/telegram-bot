package pro.sky.telegrambot.controllers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.telegrambot.configuration.BotConfig;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private UpdateController updateController;

    public TelegramBot(BotConfig config) {
        super(config.getToken());

        this.config = config;

        // Создание меню
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "Начальное меню"));
        commandList.add(new BotCommand("/test", "Создать тестовое напомининие"));
        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    public void register(UpdateController updateController) {
        if (this.updateController == null) {
            this.updateController = updateController;
        }
    }

    /***
     * Сюда падают все сообщения
     */
    @Override
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
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
