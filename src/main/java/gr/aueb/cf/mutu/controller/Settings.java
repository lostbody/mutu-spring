package gr.aueb.cf.mutu.controller;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.model.Interest;
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
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class Settings {

    private final Authentication authentication;
    private final UserRepository userRepository;
    private final UserActionRepository userActionRepository;
    private final PictureRepository pictureRepository;
    private final InterestRepository interestRepository;

    public Settings(
            Authentication authentication,
            UserRepository userRepository,
            UserActionRepository userActionRepository,
            PictureRepository pictureRepository,
            InterestRepository interestRepository
    ) {
        this.authentication = authentication;
        this.userRepository = userRepository;
        this.userActionRepository = userActionRepository;
        this.pictureRepository = pictureRepository;
        this.interestRepository = interestRepository;
    }

    @GetMapping("/settings")
    public String settingsGet(
            HttpServletRequest request,
            Model model
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Interest> interests = interestRepository.findAll();
        Set<Long> userInterestsIds = loggedUser.getInterests()
                .stream()
                .map(Interest::getId)
                .collect(Collectors.toSet());
        List<Map<String, Object>> jsPictures = loggedUser.getPictures()
                .stream()
                .map((Picture picture) -> {
                    Map<String, Object> jsPicture = new HashMap<>();
                    jsPicture.put("id", picture.getId());
                    jsPicture.put("data", picture.getImageDataString());
                    jsPicture.put("filename", picture.getFilename());
                    jsPicture.put("element$", null);
                    return jsPicture;
                })
                .toList();
        List<User> matches = userActionRepository.findMatchesByUserId(loggedUser.getId());

        model.addAttribute("user", loggedUser);
        model.addAttribute("interests", interests);
        model.addAttribute("userInterestsIds", userInterestsIds);
        model.addAttribute("pictures", jsPictures);
        model.addAttribute("matches", matches);

        return "settings";
    }
}
