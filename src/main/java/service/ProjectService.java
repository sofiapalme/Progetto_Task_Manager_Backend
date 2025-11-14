package service;

import data.model.Project;
import data.model.User;
import data.repository.ProjectRepository;
import data.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(Project project, ObjectId userId) {
        User creatore = userRepository.findById(userId);
        project.setCreatore(creatore);

        List<User> teamUsers = project.getTeam().stream()
                .map(u -> userRepository.findByEmail(u.getEmail()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        project.setTeam(teamUsers);

        projectRepository.createProject(project);
        return project;
    }

    public List<Project> getAllProjectsByUser(ObjectId userId) {
        return projectRepository.getAllProjectsByUser(userId);
    }

    public void updateProject(ObjectId projectId, Project project) {
        Project existingProject = projectRepository.findById(projectId);

        List<User> teamUsers = project.getTeam().stream()
                .map(u -> userRepository.findByEmail(u.getEmail()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        project.setTeam(teamUsers);

        project.setCreatore(existingProject.getCreatore());

        projectRepository.updateProject(projectId, project);
    }


    public boolean deleteProject(ObjectId projectId) {
        return projectRepository.deleteProject(projectId);
    }
}
