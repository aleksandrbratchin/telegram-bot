package pro.sky.telegrambot.tbotcommand.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.listener.UpdatesListener;
import pro.sky.telegrambot.utils.MessageUtils;

@Component
public class NotificationCommand implements TextCommand {

    private final MessageUtils messageUtils;
    private final UpdatesListener updatesListener;

    public NotificationCommand(MessageUtils messageUtils, UpdatesListener telegramBotUpdatesListener) {
        this.messageUtils = messageUtils;
        this.updatesListener = telegramBotUpdatesListener;
    }

    @Override
    public SendMessage process(Update update) {
        updatesListener.add(update);
        return messageUtils.generateSendMessageWithText(
                update,
                "Создано напоминание: \"" + update.getMessage().getText() + "\""
        );
    }

}
