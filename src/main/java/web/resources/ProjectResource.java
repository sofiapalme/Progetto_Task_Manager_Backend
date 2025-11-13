package web.resources;

import data.model.Project;
import data.repository.ProjectRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import service.ProjectService;

import java.util.List;

@Path("/api/projects")
public class ProjectResource  {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public ProjectResource(ProjectRepository projectRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(Project project) {
        projectRepository.createProject(project);
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> listProjectsByUser(
            @PathParam("userId") String userId
    ) {
        return projectRepository.find("team.idUser in ?1", List.of(userId)).list();
    }

    @PUT
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateProject(@PathParam("projectId") String projectId, Project project) {
        ObjectId id = new ObjectId(projectId);
        projectService.updateProject(id, project);
    }

    @DELETE
    @Path("/{projectId}")
    public void deleteProject(@PathParam("projectId") String projectId) {
        ObjectId id = new ObjectId(projectId);
        projectService.deleteProject(id);
    }

}
