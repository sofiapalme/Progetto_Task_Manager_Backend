package web.resources;
import data.model.Task;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import service.TaskService;
import web.model.token.UsernameUpdateRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@DenyAll
@Path("/task")
public class TaskResource {
    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @PermitAll
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(Task task) {
        taskService.createTask(task);
        return Response.ok().build();
    }

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks() {
        List<Task> taskList = taskService.getAllTasks();
        return Response.ok(taskList).build();
    }

    @PermitAll
    @GET
    @Path("/fase/{fase}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByFase (@PathParam("fase") String fase) {
        List<Task> taskFaseList = taskService.getTasksByFase(fase);
        return Response.ok(taskFaseList).build();
    }

    @PermitAll
    @GET
    @Path("/etichetta/{etichetta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByEtichetta (@PathParam("etichetta") String etichetta) {
        List<Task> taskEtichettaList = taskService.getTasksByEtichetta(etichetta);
        return Response.ok(taskEtichettaList).build();
    }

    @PermitAll
    @GET
    @Path("/assegnatario/{assegnatario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByAssegnatario (@PathParam("assegnatario") String assegnatario) {
        List<Task> taskAssegnatarioList = taskService.getTasksByAssegnatario(assegnatario);
        return Response.ok(taskAssegnatarioList).build();
    }

    @PermitAll
    @GET
    @Path("/scadenza/{scadenza}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByScadenza (@PathParam("scadenza") String scadenzaStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date scadenza;
        try {
            scadenza = sdf.parse(scadenzaStr);
        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Formato data non valido. Usa yyyy-MM-dd")
                    .build();
        }

        List<Task> taskScadenzaList = taskService.getTasksByScadenza(scadenza);
        return Response.ok(taskScadenzaList).build();
    }

    @PermitAll
    @GET
    @Path("/progetto/{progetto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByProgetto (@PathParam("progetto") String progettoId) {
        ObjectId progetto = new ObjectId(progettoId);
        List<Task> taskProgettoList = taskService.getTasksByProgetto(progetto);
        return Response.ok(taskProgettoList).build();
    }

    @PermitAll
    @DELETE
    @Path("/elimina/{id}")
    public void deleteTask(@PathParam("id") String id) {
        ObjectId key = new ObjectId(id);
        taskService.deleteTask(key);
    }

    @PUT
    @Path("/modifica/{id}")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("id") String id, Task task) {
        task.id = new ObjectId(id); // accesso diretto al campo id di PanacheMongoEntity
        boolean updated = taskService.updateTask(task);

        if (!updated)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Task non trovato.")
                    .build();

        return Response.ok("Task aggiornata.").build();
    }
}
