package com.task.pro.task;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.task.pro.config.AuthorizationService;
import com.task.pro.exceptions.CustomException;
import com.task.pro.exceptions.CustomExceptionStore;
import com.task.pro.task.request.TaskCreateRequest;
import com.task.pro.task.request.TaskUpdateRequest;
import com.task.pro.user.Role;
import com.task.pro.user.User;
import com.task.pro.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void taskService_GetAllTasks_Authorized() {
        // Setup test parameters
        int page = 0;
        int size = 10;
        Role userRole = Role.INDIVIDUAL;
        Long userId = 1L;

        // Stub the behavior of authorizationService methods
        when(authorizationService.getCurrentUserRole()).thenReturn(userRole);
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Create a mock Page<Task> to return from the taskRepository
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        PageImpl<Task> mockPage = new PageImpl<>(mockTasks);
        when(taskRepository.findByCreatedBy_Id(eq(userId), any(PageRequest.class))).thenReturn(mockPage);

        // Call the method under test
        List<Task> tasks = taskService.getAllTasks(page, size);

        // Assert that the returned tasks match the expected tasks
        assertEquals(mockTasks, tasks);

        // Verify that the mocked methods were called with the expected parameters
        verify(authorizationService).getCurrentUserRole();
        verify(authorizationService).getCurrentUserId();
        verify(taskRepository).findByCreatedBy_Id(eq(userId), any(PageRequest.class));
    }

    @Test
    public void taskService_GetAllTasks_Unauthorized() {
        // Setup test parameters
        int page = 0;
        int size = 10;
        Role userRole = null;
        Long userId = null;

        // Stub the behavior of authorizationService methods
        when(authorizationService.getCurrentUserRole()).thenReturn(userRole);
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        assertThrows(CustomException.class, () -> taskService.getAllTasks(page, size));

        // Verify that the mocked methods were called with the expected parameters
        verify(authorizationService).getCurrentUserRole();
        verify(authorizationService).getCurrentUserId();

        // Ensure that taskRepository.findByCreatedBy_Id() was not called
        verify(taskRepository, never()).findByCreatedBy_Id(anyLong(), any(PageRequest.class));
    }

    @Test
    public void taskService_GetTask_Authorized() {
        // Setup test parameters
        Long taskId = 1L;
        Long userId = 123L;

        // Stub the behavior of authorizationService method
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Create a mock Task object
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setCreatedBy(new User());

        // Stub the behavior of taskRepository method to return the mock task
        when(taskRepository.findByCreatedBy_IdAndId(userId, taskId)).thenReturn(Optional.of(mockTask));

        // Call the method under test
        Task retrievedTask = taskService.getTask(taskId);

        // Verify that the task returned by the service matches the mock task
        assertEquals(mockTask, retrievedTask);

        // Verify that the mocked methods were called with the expected parameters
        verify(authorizationService).getCurrentUserId();
        verify(taskRepository).findByCreatedBy_IdAndId(userId, taskId);
    }

    @Test
    public void taskService_GetTask_TaskNotFound() {
        // Setup test parameters
        Long taskId = 1L;
        Long userId = 123L;

        // Stub the behavior of authorizationService method
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Create a mock Task object
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setCreatedBy(new User());

        // Stub the behavior of taskRepository method to return the mock task
        when(taskRepository.findByCreatedBy_IdAndId(userId, taskId)).thenReturn(Optional.empty());

        // Call the method under test and catch the exception
        CustomException thrownException = assertThrows(CustomException.class, () -> taskService.getTask(taskId));

        // Verify that the exception message matches the expected message
        assertEquals(CustomExceptionStore.TASK_NOT_FOUND.getMessage(), thrownException.getMessage());
    }


    @Test
    public void taskService_CreateTask_Authorized() {
        // Setup test parameters
        Long userId = 123L;
        TaskCreateRequest taskRequest = new TaskCreateRequest();
        taskRequest.setTaskName("Test Task");
        taskRequest.setTaskPriority(TaskPriority.HIGH);
        taskRequest.setDueDate(LocalDateTime.now().plusDays(7));

        // Stub the behavior of authorizationService method
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Mock the user returned by userService
        User mockUser = new User();
        mockUser.setId(userId);
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Call the method under test
        Task createdTask = taskService.createTask(taskRequest);

        // Verify that the task was created with the expected parameters
        assertNotNull(createdTask);
        assertEquals(taskRequest.getTaskName(), createdTask.getTaskName());
        assertEquals(taskRequest.getTaskPriority(), createdTask.getTaskPriority());
        assertEquals(taskRequest.getDueDate(), createdTask.getDueDate());
        assertEquals(userId, createdTask.getCreatedBy().getId());

        // Verify that taskRepository.save() was called with the new task
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void taskService_CreateTask_Unauthorized() {
        // Setup test parameters
        TaskCreateRequest taskRequest = new TaskCreateRequest();

        // Simulate an authorization failure by throwing an exception from authorizationService
        when(authorizationService.getCurrentUserId()).thenThrow(new CustomException(CustomExceptionStore.AUTHORIZATION_FAILED));

        // Call the method under test and assert that it throws a CustomException
        assertThrows(CustomException.class, () -> taskService.createTask(taskRequest));

        // Verify that taskRepository.save() was not called
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    public void taskService_DeleteTask_Success() {
        // Setup test parameters
        Long taskId = 1L;
        Long userId = 123L;

        // Stub the behavior of authorizationService method
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Create a mock Task object
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setCreatedBy(new User());

        // Stub the behavior of taskRepository method to return the mock task
        when(taskRepository.findByCreatedBy_IdAndId(userId, taskId)).thenReturn(Optional.of(mockTask));

        // Call the method under test
        assertDoesNotThrow(() -> taskService.deleteTask(taskId));

        // Verify that taskRepository.delete() was called with the mock task
        verify(taskRepository).delete(mockTask);
    }

    @Test
    public void taskService_DeleteTask_TaskNotFound() {
        // Setup test parameters
        Long taskId = 1L;
        Long userId = 123L;

        // Stub the behavior of authorizationService method
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Simulate task not found by making taskRepository return an empty Optional
        CustomException thrownException = assertThrows(CustomException.class, () -> taskService.deleteTask(taskId));

        // Verify that the exception message matches the expected message
        assertEquals(CustomExceptionStore.TASK_NOT_FOUND.getMessage(), thrownException.getMessage());

        // Call the method under test and assert that it throws a CustomException
        assertThrows(CustomException.class, () -> taskService.deleteTask(taskId));

        // Verify that taskRepository.delete() was not called
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    public void taskService_DeleteTask_Failure() {
        // Setup test parameters
        Long taskId = 1L;
        Long userId = 123L;

        // Stub the behavior of authorizationService method
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Create a mock Task object
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setCreatedBy(new User());

        // Stub the behavior of taskRepository method to return the mock task
        when(taskRepository.findByCreatedBy_IdAndId(userId, taskId)).thenReturn(Optional.of(mockTask));

        // Simulate deletion failure by throwing an exception from taskRepository
        doThrow(new RuntimeException("Deletion failed")).when(taskRepository).delete(mockTask);

        // Call the method under test and assert that it throws a CustomException
        CustomException thrownException = assertThrows(CustomException.class, () -> taskService.deleteTask(taskId));

        // Verify that the exception message matches the expected message
        assertEquals(CustomExceptionStore.TASK_DELETION_FAILED.getMessage(), thrownException.getMessage());
    }

    @Test
    public void testUpdateTask_Success() {
        // Setup test parameters
        Long taskId = 1L;
        Long userId = 123L;

        // Create a TaskUpdateRequest with updated task details
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setTaskName("Updated Task Name");
        taskUpdateRequest.setTaskPriority(TaskPriority.HIGH);
        taskUpdateRequest.setDueDate(LocalDateTime.now().plusDays(7));
        taskUpdateRequest.setMarkedAsComplete(true);

        // Create a mock Task object with existing details
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setCreatedAt(LocalDateTime.now().minusDays(3));
        existingTask.setUpdatedAt(LocalDateTime.now().minusDays(2));
        existingTask.setTaskName("Old Task Name");
        existingTask.setTaskPriority(TaskPriority.LOW);
        existingTask.setDueDate(LocalDateTime.now().plusDays(2));
        existingTask.setMarkedAsComplete(false);
        existingTask.setCreatedBy(new User());

        // Stub the behavior of authorizationService method
        when(authorizationService.getCurrentUserId()).thenReturn(userId);

        // Stub the behavior of taskRepository method to return the mock task
        when(taskRepository.findByCreatedBy_IdAndId(userId, taskId)).thenReturn(Optional.of(existingTask));

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method under test
        Task updatedTask = taskService.updateTask(taskUpdateRequest, taskId);

        // Verify that the task was updated with the expected parameters
        assertNotNull(updatedTask);
        assertEquals(taskUpdateRequest.getTaskName(), updatedTask.getTaskName());
        assertEquals(taskUpdateRequest.getTaskPriority(), updatedTask.getTaskPriority());
        assertEquals(taskUpdateRequest.getDueDate(), updatedTask.getDueDate());
        assertEquals(taskUpdateRequest.isMarkedAsComplete(), updatedTask.getMarkedAsComplete());

        // Verify that taskRepository.save() was called with the updated task
        verify(taskRepository).save(existingTask);
    }
}
