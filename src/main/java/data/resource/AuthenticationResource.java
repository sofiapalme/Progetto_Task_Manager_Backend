package data.resource;

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

import java.util.Date;

@Path("/auth")
public class AuthenticationResource {

    private static final byte[] SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    @Inject
    MongoConfig config;

    /** Ritorna la collection users */
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

        Document userDoc = getUserCollection()
                .find(Filters.eq("email", request.getEmail()))
                .first();

        if (userDoc == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email o password errati")
                    .build();
        }

        String storedPassword = userDoc.getString("password");
        if (!storedPassword.equals(request.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email o password errati")
                    .build();
        }

        String jwt = Jwts.builder()
                .setSubject(request.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1h
                .signWith(Keys.hmacShaKeyFor(SECRET))
                .compact();

        return Response.ok(new TokenResponse(jwt)).build();
    }
}
