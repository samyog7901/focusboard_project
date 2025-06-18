package org.focusboard.controller;

import org.focusboard.model.TaskModel;
import org.focusboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "auth";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        // For now, get all tasks. In a real app, you'd filter by user
        List<TaskModel> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "task_list";
    }
}