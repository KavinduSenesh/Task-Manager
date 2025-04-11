package lk.ijse.task_manager.mapper;

import lk.ijse.task_manager.dto.TaskDTO;
import lk.ijse.task_manager.dto.UserDTO;
import lk.ijse.task_manager.entity.Task;
import lk.ijse.task_manager.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    Task taskDTOToTask(TaskDTO taskDTO);

    TaskDTO taskToTaskDTO(Task task);

    List<TaskDTO> taskListToTaskDTOList(List<Task> taskList);
}
