package com.task.pro.task;

import com.task.pro.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository
        extends JpaRepository<Task, Long> {

    Page<Task> findByCreatedBy_Id(Long userId, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.createdBy.id = :userId AND t.id = :taskId")
    Optional<Task> findByCreatedBy_IdAndId(@Param("userId") Long userId, @Param("taskId") Long taskId);
}
