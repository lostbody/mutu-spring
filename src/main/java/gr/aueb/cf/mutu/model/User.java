package gr.aueb.cf.mutu.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(name = "password", nullable = false)
    private String hashedPassword;
    @Column
    private Integer height;
    @Column
    private Integer weight;
    @Column
    private String bio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @OrderBy("seq DESC, id ASC")
    private List<Picture> pictures = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getHashedPassword() { return hashedPassword; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Set<Interest> getInterests() { return interests; }
    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }
    public List<Picture> getPictures() {
        return pictures;
    }
    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
