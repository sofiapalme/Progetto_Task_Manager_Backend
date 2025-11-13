package service;

import data.model.User;
import data.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import web.model.UserResponse;

@ApplicationScoped
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse authenticate(String email, String password) {
        User user = userRepository.authenticate(email, password);
        if (user != null) {
            return toUserResponse(user);
        }
        return null;
    }

    public UserResponse getUserByEmail(String username) {
        return toUserResponse(userRepository.findByEmail(username));
    }

    public String getIdByUser(UserResponse user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser == null) {
            return null;
        }
        return dbUser.getId().toHexString();
    }

    private static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail()
        );
    }
}
