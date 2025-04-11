package lk.ijse.task_manager.repository;

import lk.ijse.task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findTaskById(long id);

    @Query("SELECT t FROM Task t")
    List<Task> findAllTask();
}
