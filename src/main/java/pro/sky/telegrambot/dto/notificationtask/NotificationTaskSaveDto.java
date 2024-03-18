package pro.sky.telegrambot.dto.notificationtask;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class NotificationTaskSaveDto {
    @Positive
    @NotNull
    private Long chatId;
    @NotBlank
    private String message;
    @FutureOrPresent
    @NotNull
    private LocalDateTime dateNotification;

    public NotificationTaskSaveDto(@NotNull Long chatId, @NotBlank String message, @NotNull LocalDateTime dateNotification) {
        this.chatId = chatId;
        this.message = message;
        this.dateNotification = dateNotification;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(LocalDateTime dateNotification) {
        this.dateNotification = dateNotification;
    }
}



