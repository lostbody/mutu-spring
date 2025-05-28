package gr.aueb.cf.mutu.controller.dto;

public class UploadPictureResponseDto {
    private Long id;

    public UploadPictureResponseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
