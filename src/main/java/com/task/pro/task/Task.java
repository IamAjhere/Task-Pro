package com.task.pro.task;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table

public class Task  {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String taskName;
    private TaskPriority taskPriority;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private LocalDateTime dueDate;
    private Boolean markedAsComplete = false;

    public enum TaskPriority {
        HIGH,
        MEDIUM,
        LOW
    }

    public Task() {
    }

    public Task(Long id,
                String taskName,
                TaskPriority taskPriority,
                LocalDateTime createdDate,
                LocalDateTime updatedDate,
                String createdBy,
                LocalDateTime dueDate,
                Boolean markedAsComplete) {
        this.id = id;
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.dueDate = dueDate;
        this.markedAsComplete = markedAsComplete;
    }

    public Task(String taskName,
                TaskPriority taskPriority,
                LocalDateTime createdDate,
                LocalDateTime updatedDate,
                String createdBy,
                LocalDateTime dueDate,
                Boolean markedAsComplete) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
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
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", createdBy='" + createdBy + '\'' +
                ", dueDate=" + dueDate +
                ", markedAsComplete=" + markedAsComplete +
                '}';
    }
}
