package service;

import data.model.Collaborator;
import data.model.Project;
import data.model.Role;
import data.repository.ProjectRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project, ObjectId userId) {
        List<Collaborator> team = new ArrayList<>();
        team.add(new Collaborator(userId, Role.OWNER));
        project.setTeam(team);
        
        projectRepository.createProject(project);
        return project;
    }

    public List<Project> getAllProjectsByUser(ObjectId userId) {
        return projectRepository.getAllProjectsByUser(userId);
    }

    public void updateProject(ObjectId projectId, Project project) {
        projectRepository.updateProject(projectId, project);
    }

    public boolean deleteProject(ObjectId projectId) {
        return projectRepository.deleteProject(projectId);
    }
}
