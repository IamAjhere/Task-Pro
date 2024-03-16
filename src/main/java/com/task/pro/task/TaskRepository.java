package com.task.pro.task;

import com.task.pro.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository
        extends JpaRepository<Task, Long> {

    List<Task> findByCreatedBy_Id(Long userId);
}
