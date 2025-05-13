package gr.aueb.cf.mutu.controller;
import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.model.Picture;
import gr.aueb.cf.mutu.model.User;
import gr.aueb.cf.mutu.repository.InterestRepository;
import gr.aueb.cf.mutu.repository.PictureRepository;
import gr.aueb.cf.mutu.repository.UserActionRepository;
import gr.aueb.cf.mutu.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Matches {

    private final Authentication authentication;
    private final UserRepository userRepository;
    private final UserActionRepository userActionRepository;
    private final PictureRepository pictureRepository;

    public Matches(
            Authentication authentication,
            UserRepository userRepository,
            UserActionRepository userActionRepository,
            PictureRepository pictureRepository
    ) {
        this.authentication = authentication;
        this.userRepository = userRepository;
        this.userActionRepository = userActionRepository;
        this.pictureRepository = pictureRepository;
    }
    @GetMapping("/matches")
    public String matchesGet(
            HttpServletRequest request,
            Model model
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<User> matches = userActionRepository.findMatchesByUserId(loggedUser.getId());

        Map<Long, Picture> avatars = new HashMap<>();
        for (User match : matches) {
            Picture picture = pictureRepository.findFirstByUser_Id(match.getId());
            avatars.put(match.getId(), picture);
        }

        model.addAttribute("matches", matches);
        model.addAttribute("avatars", avatars);

        return "matches";
    }
}
