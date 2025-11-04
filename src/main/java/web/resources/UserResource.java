package web.resources;

import data.model.User;
import data.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository repo;

    //ADD USER
    @POST
    public Response addUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password obbligatoria.")
                    .build();
        }

        repo.add(user);
        return Response.ok("Utente aggiunto.").build();
    }

    //FIND USER BY EMAIL
    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEmail(@PathParam("email") String email) {

        User user = repo.findByEmail(email);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Utente non trovato.")
                    .build();
        }

        return Response.ok(user).build();
    }

    //UPDATE PW
    @PUT
    @Path("/{id}/password")
    public Response updatePassword(@PathParam("id") String id, String newPassword) {
        boolean updated = repo.updatePassword(id, newPassword);

        if (!updated)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Utente non trovato.")
                    .build();

        return Response.ok("Password aggiornata.").build();
    }

    //UPDATE EMAIL
    @PUT
    @Path("/{id}/email")
    public Response updateEmail(@PathParam("id") String id, String newEmail) {
        boolean updated = repo.updateEmail(id, newEmail);

        if (!updated)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Utente non trovato.")
                    .build();

        return Response.ok("Email aggiornata.").build();
    }

    //UPDATE USERNAME
    @PUT
    @Path("/{id}/username")
    public Response updateUsername(@PathParam("id") String id, String newUsername) {
        boolean updated = repo.updateUsername(id, newUsername);

        if (!updated)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Utente non trovato.")
                    .build();

        return Response.ok("Username aggiornato.").build();
    }
}
