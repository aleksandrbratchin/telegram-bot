package pro.sky.telegrambot.listener;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdatesListener {
    String getPattern();
    void registerBot(TelegramBot telegramBot);
    void add(Update update);
}
