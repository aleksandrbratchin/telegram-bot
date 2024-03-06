package pro.sky.telegrambot.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThat;

class MessageUtilsTest {

    private final MessageUtils messageUtils = new MessageUtils();
    private SendMessage expectedSendMessage;
    private final String chatId = "12345";
    private final String message = "Text message";

    @BeforeEach
    public void initEach() {
        expectedSendMessage = new SendMessage(chatId, message);
    }

    @Test
    void generateSendMessageWithText() {

        SendMessage sendMessage = messageUtils.generateSendMessageWithText(
                Long.valueOf(chatId),
                message
        );

        assertThat(sendMessage.getChatId()).isEqualTo(chatId);
        assertThat(sendMessage.getText()).isEqualTo(message);
    }

    @Test
    void testGenerateSendMessageWithText() {
        Update update = new Update();
        Message message1 = new Message();
        message1.setText(message);
        Chat chat = new Chat(Long.valueOf(chatId), " ");
        message1.setChat(chat);
        update.setMessage(message1);

        SendMessage sendMessage = messageUtils.generateSendMessageWithText(
                update,
                message
        );

        assertThat(sendMessage.getChatId()).isEqualTo(chatId);
        assertThat(sendMessage.getText()).isEqualTo(message);
    }
}