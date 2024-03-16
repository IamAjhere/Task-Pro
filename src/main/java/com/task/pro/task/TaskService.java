package com.task.pro.task;

import com.task.pro.config.AuthorizationService;
import com.task.pro.user.Role;
import com.task.pro.exceptions.CustomException;
import com.task.pro.exceptions.CustomExceptionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AuthorizationService authorizationService;

    @Autowired
    public TaskService(TaskRepository taskRepository, AuthorizationService authorizationService) {
        this.taskRepository = taskRepository;
        this.authorizationService = authorizationService;
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
}
