package pro.sky.telegrambot.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pro.sky.telegrambot.dto.notificationtask.NotificationTaskSaveDto;
import pro.sky.telegrambot.mapper.notificationtask.NotificationTaskSaveDtoMapperImpl;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NotificationTaskServiceTest {
    @Mock
    private NotificationTaskRepository repository;
    @Spy
    private NotificationTaskSaveDtoMapperImpl mapper;
    @InjectMocks
    private NotificationTaskService service;

    @Test
    void save() {
        LocalDateTime testDate = LocalDateTime.of(2099, 1, 1, 1, 1);
        NotificationTaskSaveDto taskSaveDto = new NotificationTaskSaveDto(
                123L,
                "test message",
                testDate
        );
        NotificationTask task = new NotificationTask(
                123L,
                "test message",
                testDate
        );
        Mockito.when(repository.save(any(NotificationTask.class)))
                .thenReturn(task);

        service.save(taskSaveDto);

        verify(repository, times(1)).save(task);
    }

    @Test
    void findAllNeedToSendNotificationsToNow() {
        LocalDateTime testDate = LocalDateTime.of(2099, 1, 1, 1, 1);
        List<NotificationTask> notifications = List.of(
                new NotificationTask(
                        123L,
                        "test message",
                        testDate)
        );
        Mockito.when(repository.findAllByNotificationSentFalseAndDateNotificationLessThanEqual(any(LocalDateTime.class)))
                .thenReturn(notifications
                );

        List<NotificationTask> results = service.findAllNeedToSendNotificationsToNow();

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.getFirst().isNotificationSent()).isFalse();
    }

    @Test
    void sent() {
        LocalDateTime testDate = LocalDateTime.of(2099, 1, 1, 1, 1);
        NotificationTask task = new NotificationTask(
                123L,
                "test message",
                testDate
        );
        Mockito.when(repository.save(any(NotificationTask.class)))
                .thenReturn(task);

        service.sent(task);

        verify(repository, times(1)).save(task);
    }
}