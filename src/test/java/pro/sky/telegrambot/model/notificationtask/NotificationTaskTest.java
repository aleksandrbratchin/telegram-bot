package pro.sky.telegrambot.model.notificationtask;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationTaskTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    private final ExecutableValidator executableValidator = validator.forExecutables();

    private NotificationTask notificationTask;

    @BeforeEach
    public void init() {
        LocalDateTime testDate = LocalDateTime.now().plusDays(1);
        notificationTask = new NotificationTask(
                123L,
                "test message",
                testDate
        );
        notificationTask.setId(UUID.randomUUID());
    }

    @Test
    public void success() {

        Set<ConstraintViolation<NotificationTask>> violations = validator.validate(notificationTask);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void messageIsBlankFails() {
        notificationTask.setMessage("  ");

        Set<ConstraintViolation<NotificationTask>> violations = validator.validate(notificationTask);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void nullParametersFails() {
        notificationTask.setChatId(null);
        notificationTask.setMessage(null);
        notificationTask.setDateNotification(null);

        Set<ConstraintViolation<NotificationTask>> violations = validator.validate(notificationTask);

        assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    public void constructorFails() throws NoSuchMethodException {
        var constructor = NotificationTask.class.getConstructor(Long.class, String.class, LocalDateTime.class);

        var violations = this.executableValidator
                .validateConstructorParameters(constructor, new Object[]{null, null, null});

        assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    public void constructorSuccess() throws NoSuchMethodException {
        var constructor = NotificationTask.class.getConstructor(Long.class, String.class, LocalDateTime.class);

        var violations = this.executableValidator
                .validateConstructorParameters(
                        constructor,
                        new Object[]{
                                notificationTask.getChatId(),
                                notificationTask.getMessage(),
                                notificationTask.getDateNotification()
                        }
                );

        assertThat(violations.size()).isEqualTo(0);
    }
}