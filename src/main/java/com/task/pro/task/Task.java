package com.task.pro.task;

import com.task.pro.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false)
    private String taskName;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority taskPriority;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private LocalDateTime dueDate;
    @Column(nullable = false)
    private Boolean markedAsComplete = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
