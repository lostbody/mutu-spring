package gr.aueb.cf.mutu;

import gr.aueb.cf.mutu.model.User;
import gr.aueb.cf.mutu.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;

@Service
public class Authentication {
    private final UserRepository userRepository;
    private static final SecureRandom rand = new SecureRandom();
    private static final HashMap<String, Long> sessions = new HashMap<>();

    public Authentication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verifyPassword(User user, String plainTextPassword) {
        return BCrypt.checkpw(plainTextPassword, user.getHashedPassword());
    }

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12) );
    }

    public User authenticateUser(String email, String plainTextPassword) {
        User user = userRepository.findByEmail(email);
        return user != null && verifyPassword(user, plainTextPassword) ? user : null;
    }

    public User getSessionUser(HttpServletRequest request) {
        User loggedUser = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equals("loginToken")) { continue; }
                String token = cookie.getValue();

                Long userId = sessions.get(token);
                if (userId != null) {
                    loggedUser = userRepository
                            .findById(userId)
                            .orElse(null);
                }

                break;
            }
        }

        return loggedUser;
    }

    public void createUserSession(User user, HttpServletResponse response) {
        String token;
        do {
            token = String.format("%06d", rand.nextInt(1_000_000));
        } while (sessions.containsKey(token));

        sessions.put(token, user.getId());

        Cookie cookie = new Cookie("loginToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3 * 24 * 60 * 60); // 3 days
        response.addCookie(cookie);
    }
}