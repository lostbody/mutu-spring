package gr.aueb.cf.mutu.repository;

import gr.aueb.cf.mutu.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
        SELECT m
        FROM Message m
        WHERE
            (m.sender.id = :user1Id AND m.receiver.id = :user2Id)
            OR (m.sender.id = :user2Id AND m.receiver.id = :user1Id)
        ORDER BY m.timestamp ASC
    """)
    List<Message> getConversationByUserIds(Long user1Id, Long user2Id);

    @Query("""
        SELECT m
        FROM Message m
        WHERE
            (
                (m.sender.id = :user1Id AND m.receiver.id = :user2Id)
                OR (m.sender.id = :user2Id AND m.receiver.id = :user1Id)
            )
            AND m.timestamp > :since
        ORDER BY m.timestamp ASC
    """)
    List<Message> getNewChatMessages(Long user1Id, Long user2Id, Long since);
}
