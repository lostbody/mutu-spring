package gr.aueb.cf.mutu.controller;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.controller.dto.SendMessageRequestDto;
import gr.aueb.cf.mutu.controller.dto.SendMessageResponseDto;
import gr.aueb.cf.mutu.controller.dto.UpdateMessagesResponseDto;
import gr.aueb.cf.mutu.model.Message;
import gr.aueb.cf.mutu.model.Picture;
import gr.aueb.cf.mutu.model.User;
import gr.aueb.cf.mutu.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    @PostMapping("/chat/{matchId}/send")
    @ResponseBody
    public void sendMessage(
            HttpServletRequest request,
            @PathVariable Long matchId,
            @RequestBody SendMessageRequestDto sendMessageRequestDto
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        User match = userRepository.findById(matchId).orElseThrow();

        String content = sendMessageRequestDto.getMessageTyped();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Message message = new Message(loggedUser, match, content, timestamp);

        messageRepository.save(message);
    }

    @GetMapping("/chat/{matchId}/update/{since}")
    @ResponseBody
    public UpdateMessagesResponseDto updateMessages(
            HttpServletRequest request,
            @PathVariable Long matchId,
            @PathVariable Long since
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        List<Message> messages = messageRepository.getNewChatMessages(loggedUser.getId(), matchId, since);

        List<UpdateMessagesResponseDto.Message> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            Long senderId = message.getSender().getId();
            String content = message.getContent();
            Long timestamp = message.getTimestamp().getTime();
            UpdateMessagesResponseDto.Message messageDto = new UpdateMessagesResponseDto.Message(senderId, content, timestamp);
            messageDtos.add(messageDto);
        }

        return new UpdateMessagesResponseDto(messageDtos);
    }
}
