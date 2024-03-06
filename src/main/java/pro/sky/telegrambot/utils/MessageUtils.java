package pro.sky.telegrambot.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Validated
public class MessageUtils {

    public SendMessage generateSendMessageWithText(Update update, @NotBlank String text){
        var message = update.getMessage();
        var response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        response.setText(text);
        return response;
    }

    public SendMessage generateSendMessageWithText(@NotNull @Positive Long chatId, @NotBlank String text){
        var response = new SendMessage();
        response.setChatId(chatId);
        response.setText(text);
        return response;
    }

}
