package org.focusboard.controller;

import org.focusboard.model.Project;
import org.focusboard.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService service){
        this.projectService = service;
    }

    // ✅ Authenticated users can get all projects
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    // ✅ Authenticated users can create project
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Project> createProject(@RequestBody Project project, Authentication auth){
        // Optionally use auth.getName() to associate project with logged-in user
        return ResponseEntity.ok(projectService.saveProject(project));
    }

    // ✅ Authenticated users can get single project
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Project> getProject(@PathVariable Long id){
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // ✅ Authenticated users can update a project
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails){
        var project = projectService.getProjectById(id);
        project.setName(projectDetails.getName());
        project.setOwner(projectDetails.getOwner());
        return ResponseEntity.ok(projectService.saveProject(project));
    }

    // ✅ Only admin can delete a project
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}
