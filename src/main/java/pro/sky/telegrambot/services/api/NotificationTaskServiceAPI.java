package pro.sky.telegrambot.services.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.dto.notificationtask.NotificationTaskSaveDto;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;

import java.util.List;

@Service
public interface NotificationTaskServiceAPI {
    void save(@Valid @NotNull NotificationTaskSaveDto notificationTask);

    List<NotificationTask> findAllNeedToSendNotificationsToNow();

    void sent(@NotNull NotificationTask notificationTask);
}
