package web.resources;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import data.config.MongoConfig;
import data.model.User;
import data.repository.UserRepository;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;
import web.model.token.EmailUpdateRequest;
import web.model.token.PasswordUdateRequest;
import web.model.token.UsernameUpdateRequest;

@DenyAll
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository repo;

    @Inject
    MongoConfig config;

    /** Ritorna la collection users (sincrona) */
    private MongoCollection<Document> getUserCollection() {
        MongoDatabase database = config.getClient().getDatabase(config.getDatabaseName());
        return database.getCollection("users"); // tipo com.mongodb.client.MongoCollection<Document>
    }

    // -----------------------
    // REGISTRAZIONE UTENTE
    // -----------------------
    @PermitAll
    @POST
    public Response addUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password obbligatoria.")
                    .build();
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email obbligatoria.")
                    .build();
        }

        // Normalizza email
        String email = user.getEmail().trim().toLowerCase();

        // Hash della password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Crea documento MongoDB garantendo che la password sia STRINGA
        Document doc = new Document()
                .append("email", email)
                .append("username", user.getUsername())
                .append("password", hashedPassword);

        getUserCollection().insertOne(doc);

        return Response.ok("Utente aggiunto.").build();
    }

    // -----------------------
    // TROVA UTENTE PER EMAIL
    // -----------------------
    @PermitAll
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

    // -----------------------
    // AGGIORNA PASSWORD
    // -----------------------
    @PermitAll
    @PUT
    @Path("/{id}/password")
    public Response updatePassword(@PathParam("id") String id, PasswordUdateRequest newPassword) {
        // Hash della nuova password
        String hashed = BCrypt.hashpw(newPassword.getPassword(), BCrypt.gensalt());
        boolean updated = repo.updatePassword(id, hashed);

        if (!updated)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Utente non trovato.")
                    .build();

        return Response.ok("Password aggiornata.").build();
    }

    // -----------------------
    // AGGIORNA EMAIL
    // -----------------------
    @PermitAll
    @PUT
    @Path("/{id}/email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmail(@PathParam("id") String id, EmailUpdateRequest request) {
        boolean updated = repo.updateEmail(id, request.getEmail());

        if (!updated)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Utente non trovato.")
                    .build();

        return Response.ok("Email aggiornata.").build();
    }

    // -----------------------
    // AGGIORNA USERNAME
    // -----------------------
    @PermitAll
    @PUT
    @Path("/{id}/username")
    public Response updateUsername(@PathParam("id") String id, UsernameUpdateRequest username) {
        boolean updated = repo.updateUsername(id, username.getUsername());

        if (!updated)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Utente non trovato.")
                    .build();

        return Response.ok("Username aggiornato.").build();
    }
}
