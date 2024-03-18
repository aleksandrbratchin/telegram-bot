package pro.sky.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, UUID> {
    List<NotificationTask> findAllByNotificationSentFalseAndDateNotificationLessThanEqual(LocalDateTime localDateTime);
}
