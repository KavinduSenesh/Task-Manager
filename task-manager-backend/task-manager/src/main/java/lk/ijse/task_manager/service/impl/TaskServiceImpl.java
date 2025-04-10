package lk.ijse.task_manager.service.impl;

import com.sun.source.util.TaskListener;
import jakarta.transaction.Transactional;
import lk.ijse.task_manager.dto.TaskDTO;
import lk.ijse.task_manager.entity.Task;
import lk.ijse.task_manager.mapper.ModelMapper;
import lk.ijse.task_manager.repository.TaskRepository;
import lk.ijse.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = modelMapper.taskDTOToTask(taskDTO);
        Task createdTask = taskRepository.save(task);
        return modelMapper.taskToTaskDTO(createdTask);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return modelMapper.taskListToTaskDTOList(taskRepository.findAll());
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        TaskDTO taskDTO = modelMapper.taskToTaskDTO(task);
        return taskDTO;
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask == null){
            throw new RuntimeException("Task not found with id: " + id);
        }

        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());

        Task updateTask = taskRepository.save(existingTask);
        return modelMapper.taskToTaskDTO(updateTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null){
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.delete(task);
    }
}
