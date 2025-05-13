package gr.aueb.cf.mutu.repository;

import gr.aueb.cf.mutu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT u
        FROM User u
        WHERE u.id != :userId
        AND NOT EXISTS (
            SELECT 1 FROM UserAction ua
            WHERE (ua.user1.id = :userId AND ua.user2 = u AND ua.user1Action IS NOT NULL)
            OR (ua.user2.id = :userId AND ua.user1 = u AND ua.user2Action IS NOT NULL)
        )
        ORDER BY RAND()
        LIMIT 1
    """)
    User getPotentialMatch(Long userId);

    User findByEmail(String email);
}
