package gr.aueb.cf.mutu.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "interests")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "interests")
    private Set<User> users = new HashSet<>();

    public Interest() {}

    public Interest(String name) {
        this.name = name;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }
}
