package web.resources;

import data.model.Project;
import data.repository.ProjectRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/api/projects")
public class ProjectResource  {

    private final ProjectRepository projectRepository;

    public ProjectResource(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> listProjectsByUser(
            @PathParam("userId") String userId
    ) {
        return projectRepository.find("collaboratori.idUser in ?1", List.of(userId)).list();
    }
}
