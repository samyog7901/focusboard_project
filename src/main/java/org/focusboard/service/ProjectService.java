package org.focusboard.service;


import org.focusboard.model.Project;
import org.focusboard.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository repo){
        this.projectRepository = repo;
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project saveProject(Project project){
        return projectRepository.save(project);
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
}
