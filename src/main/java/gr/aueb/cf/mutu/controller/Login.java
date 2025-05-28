package gr.aueb.cf.mutu.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.model.User;

@Controller
public class Login {

    private final Authentication authentication;

    Login (Authentication authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(HttpServletResponse response, String email, String password, Model model) {
        System.out.println("email=" + email + " password=" + password);

        User user = authentication.authenticateUser(email, password);
        if (user != null) {
            authentication.createUserSession(user, response);
            return "redirect:/swipe";
        } else {
            System.out.println("Login failed. Redirecting to login with error.");
            model.addAttribute("error", true);
            return "login";
        }
    }
}
