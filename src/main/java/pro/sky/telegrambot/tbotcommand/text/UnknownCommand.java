package pro.sky.telegrambot.tbotcommand.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.utils.MessageUtils;

@Component
public class UnknownCommand implements TextCommand {
    private final MessageUtils messageUtils;

    public UnknownCommand(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage process(Update update) {
        return messageUtils.generateSendMessageWithText(update, "Не удалось распознать команду");
    }
}
