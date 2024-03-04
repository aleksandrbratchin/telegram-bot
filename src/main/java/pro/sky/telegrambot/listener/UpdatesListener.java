package pro.sky.telegrambot.listener;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdatesListener {
    void add(Update update);
}
