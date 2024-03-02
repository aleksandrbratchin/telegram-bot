package pro.sky.telegrambot.model.notificationtask;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "message")
    private String message;
    @Column(name = "date_notification")
    private LocalDateTime dateNotification;
    @Column(name = "notification_sent")
    private boolean notificationSent;

    public NotificationTask(Long chatId, String message, LocalDateTime dateNotification) {
        this.chatId = chatId;
        this.message = message;
        this.dateNotification = dateNotification;
        this.notificationSent = false;
    }

    public NotificationTask() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return notificationSent == that.notificationSent && Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(message, that.message) && Objects.equals(dateNotification, that.dateNotification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, message, dateNotification, notificationSent);
    }
}
