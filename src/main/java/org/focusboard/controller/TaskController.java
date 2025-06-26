package org.focusboard.controller;

import org.focusboard.model.TaskModel;
import org.focusboard.model.TaskStatus;
import org.focusboard.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://192.168.194.203:5173")
@RestController
@RequestMapping("/api/tasks")

public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService service) {
        this.taskService = service;
    }

    // ✅ Get all tasks
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<TaskModel> getAllTasks() {
        return taskService.getAllTasks();
    }

    // ✅ Get tasks by project
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<TaskModel> getTasksByProject(@PathVariable Long projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    // ✅ Create new task
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskModel task, Authentication auth) {
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.NOT_STARTED);
        }

        task.setCreatedBy(auth.getName()); // email from JWT
        return ResponseEntity.ok(taskService.saveTask(task));
    }

    // ✅ Get single task by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TaskModel> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // ✅ Update task
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TaskModel> updateTask(@PathVariable Long id, @RequestBody TaskModel taskDetails) {
        TaskModel task = taskService.getTaskById(id);

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setStartDate(taskDetails.getStartDate());
        task.setEndDate(taskDetails.getEndDate());
        task.setPriority(taskDetails.getPriority());
        task.setCategory(taskDetails.getCategory());
        task.setProject(taskDetails.getProject());

        return ResponseEntity.ok(taskService.saveTask(task));
    }

    // ✅ Delete task (admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
