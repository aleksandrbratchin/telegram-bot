package pro.sky.telegrambot.services.api;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;

import java.util.List;

@Service
public interface NotificationTaskServiceAPI {
    void save(NotificationTask notificationTask);

    List<NotificationTask> findAllNeedToSendNotificationsToNow();

    void sent(NotificationTask notificationTask);
}
