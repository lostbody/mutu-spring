package gr.aueb.cf.mutu.controller.dto;

public class SendMessageRequestDto {
    private String messageTyped;

    public SendMessageRequestDto() {
    }

    public String getMessageTyped() {
        return messageTyped;
    }

    public void setMessageTyped(String messageTyped) {
        this.messageTyped = messageTyped;
    }
}
