package web.resources;

import data.model.Project;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;
import service.AuthorizationService;
import service.ProjectService;

import java.util.List;

@Path("/api/projects")
public class ProjectResource  {
    private final ProjectService projectService;
    private final AuthorizationService authorizationService;
    private final JsonWebToken jwt;

    public ProjectResource(ProjectService projectService, AuthorizationService authorizationService, JsonWebToken jwt) {
        this.projectService = projectService;
        this.authorizationService = authorizationService;
        this.jwt = jwt;
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
    public Response deleteProject(@PathParam("projectId") String projectId) {
        ObjectId id = new ObjectId(projectId);
        String email = jwt.getName();

        boolean authorized = authorizationService.canDeleteProject(id, email);

        if (!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean deleted = projectService.deleteProject(id);

        if (!deleted)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Progetto non trovato.")
                    .build();

        return Response.ok("Progetto eliminato.").build();
    }
}
