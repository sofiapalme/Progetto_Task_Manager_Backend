package service;

import data.model.Project;
import data.model.User;
import data.repository.ProjectRepository;
import data.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class AuthorizationService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public AuthorizationService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public boolean canDeleteProject(ObjectId projectId, String email) {
        Project project = projectRepository.findById(projectId);

        if(project == null) return false;

        User creatore = project.getCreatore();
        User utenteLoggato = userRepository.findByEmail(email);

        if (creatore == null) return false;

        return creatore.getId().equals(utenteLoggato.getId());
    }
}
