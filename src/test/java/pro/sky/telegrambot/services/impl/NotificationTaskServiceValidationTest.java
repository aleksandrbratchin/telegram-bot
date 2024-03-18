package pro.sky.telegrambot.services.impl;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.telegrambot.dto.notificationtask.NotificationTaskSaveDto;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationTaskServiceValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    private final ExecutableValidator executableValidator = validator.forExecutables();

    NotificationTaskService notificationTaskService = new NotificationTaskService(null, null);

    @BeforeEach
    public void init() {
    }

    @Test
    public void sentSuccess() throws NoSuchMethodException {
        LocalDateTime testDate = LocalDateTime.now().plusDays(1);
        var notificationTask = new NotificationTask(
                123L,
                "test message",
                testDate
        );

        var method = NotificationTaskService.class.getMethod("sent", NotificationTask.class);

        var violations = this.executableValidator
                .validateParameters(
                        notificationTaskService,
                        method,
                        new Object[]{
                                notificationTask
                        }
                );

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void sentFails() throws NoSuchMethodException {

        var method = NotificationTaskService.class.getMethod("sent", NotificationTask.class);

        var violations = this.executableValidator
                .validateParameters(
                        notificationTaskService,
                        method,
                        new Object[]{
                                null
                        }
                );

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void saveSuccess() throws NoSuchMethodException {
        LocalDateTime testDate = LocalDateTime.now().plusDays(1);
        var notificationTask = new NotificationTaskSaveDto(
                123L,
                "test message",
                testDate
        );

        var method = NotificationTaskService.class.getMethod("save", NotificationTaskSaveDto.class);

        var violations = this.executableValidator
                .validateParameters(
                        notificationTaskService,
                        method,
                        new Object[]{
                                notificationTask
                        }
                );

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void saveFieldsIsIncorrectFails() throws NoSuchMethodException {
        LocalDateTime testDate = LocalDateTime.now().minusDays(1);
        var notificationTask = new NotificationTaskSaveDto(
                -123L,
                "  ",
                testDate
        );

        var method = NotificationTaskService.class.getMethod("save", NotificationTaskSaveDto.class);

        var violations = this.executableValidator
                .validateParameters(
                        notificationTaskService,
                        method,
                        new Object[]{
                                notificationTask
                        }
                );

        assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    public void saveFieldsIsNullFails() throws NoSuchMethodException {
        var notificationTask = new NotificationTaskSaveDto(
                null,
                null,
                null
        );

        var method = NotificationTaskService.class.getMethod("save", NotificationTaskSaveDto.class);

        var violations = this.executableValidator
                .validateParameters(
                        notificationTaskService,
                        method,
                        new Object[]{
                                notificationTask
                        }
                );

        assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    public void saveParameterIsNullFails() throws NoSuchMethodException {

        var method = NotificationTaskService.class.getMethod("save", NotificationTaskSaveDto.class);

        var violations = this.executableValidator
                .validateParameters(
                        notificationTaskService,
                        method,
                        new Object[]{
                                null
                        }
                );

        assertThat(violations.size()).isEqualTo(1);
    }
}