package web.resources;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import data.config.MongoConfig;
import data.model.LoginRequest;
import data.model.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

@Path("/auth")
public class AuthenticationResource {

    private static final byte[] SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    @Inject
    MongoConfig config;

    private MongoCollection<Document> getUserCollection() {
        return config.getClient()
                .getDatabase(config.getDatabaseName())
                .getCollection("users");
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email e password obbligatorie.")
                    .build();
        }

        String email = request.getEmail().trim().toLowerCase();

        Document userDoc = getUserCollection()
                .find(Filters.eq("email", email))
                .first();

        if (userDoc == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email o password errati")
                    .build();
        }

        Object pwObj = userDoc.get("password");
        if (pwObj == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email o password errati")
                    .build();
        }

        String storedHash = pwObj.toString().trim();

        boolean valid;
        try {
            valid = BCrypt.checkpw(request.getPassword(), storedHash);
        } catch (IllegalArgumentException e) {
            valid = false;
        }

        if (!valid) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email o password errati")
                    .build();
        }

        String jwt = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1h
                .signWith(Keys.hmacShaKeyFor(SECRET))
                .compact();

        return Response.ok(new TokenResponse(jwt)).build();
    }
}
