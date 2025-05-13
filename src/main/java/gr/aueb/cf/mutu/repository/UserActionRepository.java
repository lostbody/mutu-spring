package gr.aueb.cf.mutu.repository;

import gr.aueb.cf.mutu.model.User;
import gr.aueb.cf.mutu.model.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {

    @Query("SELECT ua FROM UserAction ua WHERE " +
            "(ua.user1.id = :user1Id AND ua.user2.id = :user2Id) OR " +
            "(ua.user1.id = :user2Id AND ua.user2.id = :user1Id)")
    UserAction getByUserIds(Long user1Id, Long user2Id);

    @Query("""
        SELECT u
        FROM User u
            JOIN UserAction ua
                ON (ua.user1.id = u.id OR ua.user2.id = u.id)
        WHERE
            (ua.user1.id = :userId OR ua.user2.id = :userId)
            AND u.id != :userId
            AND ua.user1Action = 'SWIPE_RIGHT'
            AND ua.user2Action = 'SWIPE_RIGHT'
    """)
    List<User> findMatchesByUserId(Long userId);

}
