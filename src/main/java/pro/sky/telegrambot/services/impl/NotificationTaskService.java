package pro.sky.telegrambot.services.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pro.sky.telegrambot.dto.notificationtask.NotificationTaskSaveDto;
import pro.sky.telegrambot.mapper.notificationtask.NotificationTaskSaveDtoMapper;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;
import pro.sky.telegrambot.services.api.NotificationTaskServiceAPI;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@Transactional
public class NotificationTaskService implements NotificationTaskServiceAPI {

    private final NotificationTaskRepository notificationTaskRepository;

    private final NotificationTaskSaveDtoMapper notificationTaskSaveDtoMapper;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository, NotificationTaskSaveDtoMapper notificationTaskSaveDtoMapper) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.notificationTaskSaveDtoMapper = notificationTaskSaveDtoMapper;
    }

    @Override
    public void save(@Valid @NotNull NotificationTaskSaveDto notificationTask) {

        notificationTaskRepository.save(notificationTaskSaveDtoMapper.fromDto(notificationTask));
    }

    @Override
    public List<NotificationTask> findAllNeedToSendNotificationsToNow() {
        return notificationTaskRepository.findAllByNotificationSentFalseAndDateNotificationLessThanEqual(
                LocalDateTime.now()
        );
    }

    @Override
    public void sent(@NotNull NotificationTask notificationTask) {
        notificationTask.setNotificationSent(true);
        notificationTaskRepository.save(notificationTask);
    }
}
