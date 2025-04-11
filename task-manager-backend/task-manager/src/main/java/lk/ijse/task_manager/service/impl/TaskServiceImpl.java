package lk.ijse.task_manager.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.task_manager.dto.TaskDTO;
import lk.ijse.task_manager.entity.Task;
import lk.ijse.task_manager.mapper.ModelMapper;
import lk.ijse.task_manager.repository.TaskRepository;
import lk.ijse.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    @Qualifier("modelMapperImpl")
    private final ModelMapper taskMapper;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task newTask = taskMapper.taskDTOToTask(taskDTO);
        Task saveTask = taskRepository.save(newTask);

        return taskMapper.taskToTaskDTO(saveTask);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        return taskMapper.taskListToTaskDTOList(taskList);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task existingTask = taskRepository.findTaskById(id);
        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        TaskDTO taskDto = taskMapper.taskToTaskDTO(existingTask);
        return taskDto;
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findTaskById(id);

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + id);
        }

        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());

        Task updatedTask = taskRepository.save(existingTask);

        return taskMapper.taskToTaskDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task existingTask = taskRepository.findTaskById(id);

        if (existingTask == null) {
            throw new RuntimeException("Task not found with id: " + id);
        }

        taskRepository.delete(existingTask);
    }
}
