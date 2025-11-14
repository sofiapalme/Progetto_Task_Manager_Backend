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

    private final ProjectService projectService;

    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @POST
    @Path("/create/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(@PathParam("userId") String userId,
                                  Project project) {
        ObjectId id = new ObjectId(userId);
        projectService.createProject(project, id);
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> listProjectsByUser(
            @PathParam("userId") String userId
    ) {
        ObjectId id = new ObjectId(userId);
        return projectService.getAllProjectsByUser(id);
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
