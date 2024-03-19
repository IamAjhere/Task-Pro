package com.task.pro.task;

import com.task.pro.config.AuthorizationService;
import com.task.pro.task.request.TaskCreateRequest;
import com.task.pro.task.request.TaskUpdateRequest;
import com.task.pro.user.Role;
import com.task.pro.exceptions.CustomException;
import com.task.pro.exceptions.CustomExceptionStore;
import com.task.pro.user.User;
import com.task.pro.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    public List<Task> getAllTasks(int page, int size) {
        Role userRole;
        Long userId;
        try {
            userRole = authorizationService.getCurrentUserRole();
            userId = authorizationService.getCurrentUserId();
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionStore.AUTHORIZATION_FAILED);
        }

        if (userRole == Role.INDIVIDUAL || userRole == Role.TEAM_OWNER) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            Page<Task> taskPage = taskRepository.findByCreatedBy_Id(userId, pageRequest);
            return taskPage.getContent();
        } else {
            throw new CustomException(CustomExceptionStore.NO_PERMISSION);
        }
    }

    public Task getTask(Long id) {
        Long userId;
        Task task;
        try {
            userId = authorizationService.getCurrentUserId();
            task = taskRepository.findByCreatedBy_IdAndId(userId, id)
                    .orElseThrow(() -> new CustomException(CustomExceptionStore.TASK_NOT_FOUND));
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionStore.AUTHORIZATION_FAILED);
        }

        if (task != null) {
            return task;
        } else {
            throw new CustomException(CustomExceptionStore.TASK_NOT_FOUND);
        }
    }
    @Transactional
    public Task createTask(TaskCreateRequest taskRequest) {
        Long userId;
        User user;
        Task task;
        try {
            userId = authorizationService.getCurrentUserId();
            user = userService.getUserById(userId);
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionStore.AUTHORIZATION_FAILED);
        }

        task = Task.builder()
                .taskName(taskRequest.getTaskName())
                .taskPriority(taskRequest.getTaskPriority())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .markedAsComplete(false)
                .dueDate(taskRequest.getDueDate())
                .createdBy(user)
                .build();

        try {
            taskRepository.save(task);
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionStore.TASK_CREATION_FAILED);
        }

        return task;
    }
    @Transactional
    public void deleteTask(Long id) {
        Long userId;
        Task task;
        try {
            userId = authorizationService.getCurrentUserId();
            task = taskRepository.findByCreatedBy_IdAndId(userId, id)
                    .orElseThrow(() -> new CustomException(CustomExceptionStore.TASK_NOT_FOUND));
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionStore.AUTHORIZATION_FAILED);
        }

        try {
            taskRepository.delete(task);
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionStore.TASK_DELETION_FAILED);
        }
    }

    @Transactional
    public Task updateTask(TaskUpdateRequest taskUpdateRequest, Long id) {
        Long userId;
        Task task;
        try {
            userId = authorizationService.getCurrentUserId();
            task = taskRepository.findByCreatedBy_IdAndId(userId, id)
                    .orElseThrow(() -> new CustomException(CustomExceptionStore.TASK_NOT_FOUND));
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionStore.AUTHORIZATION_FAILED);
        }

        if (taskUpdateRequest.getTaskName() != null) {
            task.setTaskName(taskUpdateRequest.getTaskName());
        }

        if (taskUpdateRequest.getTaskPriority() != null) {
            task.setTaskPriority(taskUpdateRequest.getTaskPriority());
        }

        if (taskUpdateRequest.getDueDate() != null) {
            task.setDueDate(taskUpdateRequest.getDueDate());
        }

        task.setMarkedAsComplete(taskUpdateRequest.isMarkedAsComplete());

        return taskRepository.save(task);
    }
}
