package org.focusboard.service;

import org.focusboard.model.TaskModel;
import org.focusboard.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository repo){
        this.taskRepository = repo;
    }

    public List<TaskModel> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<TaskModel> getTasksByProjectId(Long projectId){
        return taskRepository.findByProjectId(projectId);
    }

    public TaskModel saveTask(TaskModel task){
        return taskRepository.save(task);
    }

    public TaskModel getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
