package web.resources;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claims;
import service.UserService;
import web.model.UserResponse;
import web.model.token.AccessTokenResponse;
import web.model.LoginRequest;
import web.model.token.TokenResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Path("/auth")
public class AuthenticationResource {
    private final UserService userService;

    public AuthenticationResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        UserResponse user = userService.authenticate(request.getEmail(), request.getPassword());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        String accessToken = getAccessToken(user);
        String refreshToken = getRefreshToken(user);
        return Response.ok(new TokenResponse(accessToken, refreshToken)).build();
    }

    @POST
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"refresh_token"})
    public Response refresh(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        UserResponse user = userService.getUserByEmail(email);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        String accessToken = getAccessToken(user);
        return Response.ok(new AccessTokenResponse(accessToken)).build();
    }

    private String getAccessToken(UserResponse user) {
        String id = userService.getIdByUser(user);

        String token = Jwt
                .issuer("demo-quarkus-jwt")
                .subject(user.getEmail())
                .upn(user.getEmail())
                .groups(Set.of("access_token"))
                .claim(Claims.nickname.name(), user.getEmail())
                .claim("id", id)
                .expiresIn(Duration.ofMinutes(10))
                .issuedAt(Instant.now())
                .sign();

        return token;
    }

    private String getRefreshToken(UserResponse user) {
        String id = userService.getIdByUser(user);

        String token = Jwt
                .issuer("demo-quarkus-jwt")
                .subject(user.getEmail())
                .upn(user.getEmail())
                .groups(Set.of("refresh_token"))
                .claim(Claims.nickname.name(), user.getEmail())
                .claim("id", id)
                .expiresIn(Duration.ofHours(1))
                .issuedAt(Instant.now())
                .sign();
        return token;
    }
}
