package com.task.pro.task;

import com.task.pro.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Task  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    @Enumerated(value = EnumType.STRING)
    private TaskPriority taskPriority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private Boolean markedAsComplete = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
