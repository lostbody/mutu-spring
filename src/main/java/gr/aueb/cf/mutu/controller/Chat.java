package gr.aueb.cf.mutu.controller;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.model.Message;
import gr.aueb.cf.mutu.model.Picture;
import gr.aueb.cf.mutu.model.User;
import gr.aueb.cf.mutu.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class Chat {

    private final Authentication authentication;
    private final UserRepository userRepository;
    private final UserActionRepository userActionRepository;
    private final PictureRepository pictureRepository;
    private final InterestRepository interestRepository;
    private final MessageRepository messageRepository;

    public Chat(
            Authentication authentication,
            UserRepository userRepository,
            UserActionRepository userActionRepository,
            PictureRepository pictureRepository,
            InterestRepository interestRepository, MessageRepository messageRepository
    ) {
        this.authentication = authentication;
        this.userRepository = userRepository;
        this.userActionRepository = userActionRepository;
        this.pictureRepository = pictureRepository;
        this.interestRepository = interestRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/chat/{matchId}")
    public String chatGet(
            HttpServletRequest request,
            @PathVariable Long matchId,
            Model model
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        User match = userRepository.findById(matchId).orElseThrow();
        Picture avatar = pictureRepository.findFirstByUser_Id(matchId);
        List<Message> messages = messageRepository.getConversationByUserIds(loggedUser.getId(), matchId);

        model.addAttribute("match", match);
        model.addAttribute("avatar", avatar == null ? "/static/img/no-profile-pic.jpg" : avatar.getImageDataString());
        model.addAttribute("messages", messages);

        return "chat";
    }
}
