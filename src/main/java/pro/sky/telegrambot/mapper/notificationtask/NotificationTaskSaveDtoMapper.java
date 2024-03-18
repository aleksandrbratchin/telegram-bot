package pro.sky.telegrambot.mapper.notificationtask;

import org.mapstruct.Mapper;
import pro.sky.telegrambot.dto.notificationtask.NotificationTaskSaveDto;
import pro.sky.telegrambot.model.notificationtask.NotificationTask;

@Mapper(componentModel = "spring")
public interface NotificationTaskSaveDtoMapper {
    NotificationTask fromDto(NotificationTaskSaveDto dto);
}
