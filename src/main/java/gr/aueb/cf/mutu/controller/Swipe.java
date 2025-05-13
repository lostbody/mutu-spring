package gr.aueb.cf.mutu.controller;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.model.Interest;
import gr.aueb.cf.mutu.model.Picture;
import gr.aueb.cf.mutu.model.User;
import gr.aueb.cf.mutu.model.UserAction;
import gr.aueb.cf.mutu.repository.InterestRepository;
import gr.aueb.cf.mutu.repository.PictureRepository;
import gr.aueb.cf.mutu.repository.UserActionRepository;
import gr.aueb.cf.mutu.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
public class Swipe {

    private final Authentication authentication;
    private final UserRepository userRepository;
    private final UserActionRepository userActionRepository;
    private final PictureRepository pictureRepository;
    private final InterestRepository interestRepository;

    public Swipe(
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

    @GetMapping("/swipe")
    public String swipeGet(
            HttpServletRequest request,
            Model model
    ) {
        try {
            User loggedUser = authentication.getSessionUser(request);
            if (loggedUser == null) {
                return "redirect:/login";
            }

            User other = userRepository.getPotentialMatch(loggedUser.getId());

            if (other == null) {
                model.addAttribute("noMatches", true);
                return "swipe";
            }

            LocalDate birthday = other.getBirthday();
            LocalDateTime today = LocalDateTime.now();
            int age = today.getYear() - birthday.getYear();

            List<Picture> pictures = new ArrayList<>();
            List<Interest> interests = new ArrayList<>();

            List<Picture> userPictures = other.getPictures();
            if (userPictures != null && !userPictures.isEmpty()) {
                pictures.addAll(userPictures);
            }

            Set<Interest> userInterests = other.getInterests();
            if (userInterests != null && !userInterests.isEmpty()) {
                interests.addAll(userInterests);
            }

            model.addAttribute("user", other);
            model.addAttribute("age", age);
            model.addAttribute("pictures", pictures);
            model.addAttribute("interests", interests);
            model.addAttribute("noMatches", false);

            return "swipe";
        } catch (Exception e) {
            System.err.println("Critical error in swipe page: " + e.getMessage());
            e.printStackTrace();

            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "error";
        }
    }

    @PostMapping("/swipe")
    public String swipePost(
            HttpServletRequest request,
            @RequestParam("other-user-id") Long otherUserId,
            @RequestParam("swipe-direction") String swipeDirection,
            Model model
    ) {
        try {
            User loggedUser = authentication.getSessionUser(request);
            if (loggedUser == null) {
                return "redirect:/login";
            }

            UserAction userAction = userActionRepository.getByUserIds(loggedUser.getId(), otherUserId);

            UserAction.Action action = swipeDirection.equals("SWIPE-LEFT") ?
                    UserAction.Action.SWIPE_LEFT : UserAction.Action.SWIPE_RIGHT;

            if (userAction != null) {
                if (Objects.equals(userAction.getUser1().getId(), loggedUser.getId())) {
                    userAction.setUser1Action(action);
                } else {
                    userAction.setUser2Action(action);
                }
                userActionRepository.save(userAction);
            } else {
                User otherUser = userRepository.findById(otherUserId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user2 ID"));
                userAction = new UserAction(loggedUser, otherUser, action, null);
                userActionRepository.save(userAction);
            }

            return "redirect:/swipe";
        } catch (Exception e) {
            System.err.println("Error processing swipe: " + e.getMessage());
            e.printStackTrace();

            model.addAttribute("errorMessage", "An error occurred while processing your action.");
            return "error";
        }
    }
}
