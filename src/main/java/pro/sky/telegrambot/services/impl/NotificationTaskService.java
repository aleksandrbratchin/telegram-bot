package pro.sky.telegrambot.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;
import pro.sky.telegrambot.services.api.NotificationTaskServiceAPI;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationTaskService implements NotificationTaskServiceAPI {

    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Override
    public void save(NotificationTask notificationTask) {
        notificationTaskRepository.save(notificationTask);
    }

    @Override
    public List<NotificationTask> findAllNeedToSendNotificationsToNow() {
        return notificationTaskRepository.findAllByNotificationSentFalseAndDateNotificationLessThanEqual(
                LocalDateTime.now()
        );
    }

    @Override
    public void sent(NotificationTask notificationTask) {
        notificationTask.setNotificationSent(true);
        save(notificationTask);
    }
}
