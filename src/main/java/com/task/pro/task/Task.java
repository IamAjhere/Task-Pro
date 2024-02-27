package com.task.pro.task;

import com.task.pro.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table

public class Task  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    private TaskPriority taskPriority;
    private LocalDateTime createdAtDate;
    private LocalDateTime updatedAtDate;
    private LocalDateTime dueDate;
    private Boolean markedAsComplete = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
    public enum TaskPriority {
        HIGH,
        MEDIUM,
        LOW
    }

    public Task() {
    }

    public Task(Long id, String taskName, TaskPriority taskPriority, LocalDateTime createdAtDate, LocalDateTime updatedAtDate, LocalDateTime dueDate, Boolean markedAsComplete, User createdBy) {
        this.id = id;
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.createdAtDate = createdAtDate;
        this.updatedAtDate = updatedAtDate;
        this.dueDate = dueDate;
        this.markedAsComplete = markedAsComplete;
        this.createdBy = createdBy;
    }

    public Task(String taskName,
                TaskPriority taskPriority,
                LocalDateTime createdAtDate,
                LocalDateTime updatedDate,
                User createdBy,
                LocalDateTime dueDate,
                Boolean markedAsComplete) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.createdAtDate = createdAtDate;
        this.updatedAtDate = updatedDate;
        this.createdBy = createdBy;
        this.dueDate = dueDate;
        this.markedAsComplete = markedAsComplete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public LocalDateTime getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(LocalDateTime createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public LocalDateTime getUpdatedAtDate() {
        return updatedAtDate;
    }

    public void setUpdatedAtDate(LocalDateTime updatedAtDate) {
        this.updatedAtDate = updatedAtDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getMarkedAsComplete() {
        return markedAsComplete;
    }

    public void setMarkedAsComplete(Boolean markedAsComplete) {
        this.markedAsComplete = markedAsComplete;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskPriority=" + taskPriority +
                ", createdDate=" + createdAtDate +
                ", updatedDate=" + updatedAtDate +
                ", createdBy='" + createdBy + '\'' +
                ", dueDate=" + dueDate +
                ", markedAsComplete=" + markedAsComplete +
                '}';
    }
}
