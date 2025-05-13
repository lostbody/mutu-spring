package gr.aueb.cf.mutu.model;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(columnDefinition = "LONGBLOB NOT NULL")
    private byte[] imageData;

    @Column(nullable = false)
    private int seq;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Picture() {
    }

    public Picture(String filename, byte[] imageData, int seq, User user) {
        this.filename = filename;
        this.imageData = imageData;
        this.seq = seq;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getImageDataString() {
        return new String(imageData);
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int order) {
        this.seq = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public String getBase64Image() {
//        return Base64.getEncoder().encodeToString(this.imageData);
//    }
}
