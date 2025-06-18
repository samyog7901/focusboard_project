package org.focusboard.controller;

import org.focusboard.model.TaskModel;
import org.focusboard.model.TaskStatus;
import org.focusboard.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService service){
        this.taskService = service;
    }

    // ✅ Authenticated users (ROLE_USER or ROLE_ADMIN) can view all tasks
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<TaskModel> getAllTasks(){
        return taskService.getAllTasks();
    }

    // ✅ Authenticated users can view tasks by project
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<TaskModel> getTasksByProject(@PathVariable Long projectId){
        return taskService.getTasksByProjectId(projectId);
    }

    // ✅ Authenticated users can create a task
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskModel task, Authentication auth) {
        // Ensure status is not null; set default if missing
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.NOT_STARTED);
        }

        // Optionally track createdBy using auth.getName() (email from JWT)
         task.setCreatedBy(auth.getName());

        return ResponseEntity.ok(taskService.saveTask(task));
    }


    // ✅ Authenticated users can get a single task
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TaskModel> getTask(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // ✅ Authenticated users can update a task
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TaskModel> updateTask(@PathVariable Long id, @RequestBody TaskModel taskDetails){
        var task = taskService.getTaskById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setProject(taskDetails.getProject());
        return ResponseEntity.ok(taskService.saveTask(task));
    }

    // ✅ Only admins can delete a task
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
