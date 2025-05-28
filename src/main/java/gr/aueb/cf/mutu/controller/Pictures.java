package gr.aueb.cf.mutu.controller;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.controller.dto.UploadPictureRequestDto;
import gr.aueb.cf.mutu.controller.dto.UploadPictureResponseDto;
import gr.aueb.cf.mutu.model.Picture;
import gr.aueb.cf.mutu.model.User;
import gr.aueb.cf.mutu.repository.InterestRepository;
import gr.aueb.cf.mutu.repository.PictureRepository;
import gr.aueb.cf.mutu.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Controller
public class Pictures {
    private final Authentication authentication;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final InterestRepository interestRepository;

    public Pictures(
            Authentication authentication,
            UserRepository userRepository,
            PictureRepository pictureRepository,
            InterestRepository interestRepository
    ) {
        this.authentication = authentication;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.interestRepository = interestRepository;
    }

    @PostMapping("/pictures")
    public UploadPictureResponseDto uploadPicture(HttpServletRequest request,
                              @RequestBody UploadPictureRequestDto uploadPictureRequestDto
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Picture createdPicture = new Picture(uploadPictureRequestDto.getFilename(), uploadPictureRequestDto.getImageData().getBytes(), 5, loggedUser);

        pictureRepository.save(createdPicture);

        return new UploadPictureResponseDto(createdPicture.getId());
    }

    public enum Direction {LEFT, RIGHT}

    @PostMapping("/pictures/{pictureId}/move/{direction}")
    @ResponseStatus(HttpStatus.OK)
    public void reorderPicture(HttpServletRequest request,
                              @PathVariable Long pictureId,
                              @PathVariable Direction direction
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        List<Picture> userPictures = loggedUser.getPictures();

        int pictureIndex = IntStream.range(0, userPictures.size())
                .filter(i -> Objects.equals(userPictures.get(i).getId(), pictureId))
                .findFirst()
                .orElseThrow();

        int otherIndex;
        if (direction == Direction.LEFT) {
            otherIndex = pictureIndex - 1;
        } else {
            otherIndex = pictureIndex + 1;
        }

        Picture picture = userPictures.get(pictureIndex);
        Picture other = userPictures.get(otherIndex);

        int temp = picture.getSeq();
        picture.setSeq(other.getSeq());
        other.setSeq(temp);

        pictureRepository.save(picture);
        pictureRepository.save(other);
    }

    @DeleteMapping("/pictures/{pictureId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePicture(HttpServletRequest request,
                              @PathVariable Long pictureId
    ) {
        User loggedUser = authentication.getSessionUser(request);
        if (loggedUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        pictureRepository.deleteById(pictureId);
    }
}
