package gr.aueb.cf.mutu.repository;
import gr.aueb.cf.mutu.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture findFirstByUser_Id(Long userId);
}
