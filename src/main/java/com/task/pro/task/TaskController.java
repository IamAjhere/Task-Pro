package com.task.pro.task;

import com.task.pro.config.AuthorizationService;
import com.task.pro.exceptions.CustomException;
import com.task.pro.exceptions.CustomExceptionStore;
import com.task.pro.task.request.TaskCreateRequest;
import com.task.pro.task.request.TaskUpdateRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final AuthorizationService authorizationService;


    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper, AuthorizationService authorizationService) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(@PageableDefault(size = 5) Pageable pageable) {
        if (authorizationService.hasAnyAuthority("TASK:VIEW")) {
            List<TaskDTO> tasks = taskMapper.toTaskDTOList(
                    taskService.getAllTasks(pageable.getPageNumber(), pageable.getPageSize())
            );
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            throw new CustomException(CustomExceptionStore.NO_PERMISSION);
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id){
        if (authorizationService.hasAnyAuthority("TASK:VIEW")) {
            TaskDTO tasks = taskMapper.toTaskDTO(taskService.getTask(id));
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            throw new CustomException(CustomExceptionStore.NO_PERMISSION);
        }
    }

    @PutMapping(path="/update/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest taskUpdateRequest){
        if (authorizationService.hasAnyAuthority("TASK:UPDATE")) {
            TaskDTO tasks = taskMapper.toTaskDTO(taskService.updateTask(taskUpdateRequest,id));
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            throw new CustomException(CustomExceptionStore.NO_PERMISSION);
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskCreateRequest taskCreateRequest){
        if (authorizationService.hasAnyAuthority("TASK:ADD")) {
            TaskDTO tasks = taskMapper.toTaskDTO(taskService.createTask(taskCreateRequest));
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            throw new CustomException(CustomExceptionStore.NO_PERMISSION);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable Long id) {
        if (authorizationService.hasAnyAuthority("TASK:DELETE")) {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new CustomException(CustomExceptionStore.NO_PERMISSION);
        }
    }
}
