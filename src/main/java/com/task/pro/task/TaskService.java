package com.task.pro.task;

import com.task.pro.config.AuthorizationService;
import com.task.pro.task.request.TaskCreateRequest;
import com.task.pro.user.Role;
import com.task.pro.exceptions.CustomException;
import com.task.pro.exceptions.CustomExceptionStore;
import com.task.pro.user.User;
import com.task.pro.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AuthorizationService authorizationService;

    private final UserService userService;
    @Autowired
    public TaskService(TaskRepository taskRepository, AuthorizationService authorizationService, UserService userService) {
        this.taskRepository = taskRepository;
        this.authorizationService = authorizationService;
        this.userService = userService;
    }


    public List<Task> getTasks() {
        try {
            Role userRole = authorizationService.getCurrentUserRole();
            Long userId = authorizationService.getCurrentUserId();
            if (userRole == Role.INDIVIDUAL || userRole == Role.TEAM_OWNER) {
                return taskRepository.findByCreatedBy_Id(userId);
            }
            else {
                throw new CustomException(CustomExceptionStore.NO_PERMISSION);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Task createTask(@RequestBody TaskCreateRequest taskRequest) {
        Long userId = authorizationService.getCurrentUserId();
        User user = userService.getUserById(userId);

        Task task = Task.builder()
                .taskName(taskRequest.getTaskName())
                .taskPriority(taskRequest.getTaskPriority())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .dueDate(taskRequest.getDueDate())
                .createdBy(user)
                .build();

        taskRepository.save(task);

        return task;
    }
}
