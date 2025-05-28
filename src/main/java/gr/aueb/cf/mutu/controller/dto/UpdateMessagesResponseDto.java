package gr.aueb.cf.mutu.controller.dto;

import java.util.List;

public class UpdateMessagesResponseDto {
    public static class Message{
        private Long senderId;
        private String content;
        private Long timestamp;

        public Message(Long senderId, String content, Long timestamp) {
            this.senderId = senderId;
            this.content = content;
            this.timestamp = timestamp;
        }

        public Message() {
        }

        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }

    private List<Message> messages;

    public UpdateMessagesResponseDto(List<Message> messages) {
        this.messages = messages;
    }

    public UpdateMessagesResponseDto() {
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
