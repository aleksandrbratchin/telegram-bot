package pro.sky.telegrambot.tbotcommand.text;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TextCommand {
    SendMessage process(Update update);
}
