package gr.aueb.cf.mutu.repository;

import gr.aueb.cf.mutu.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture findFirstByUser_Id(Long userId);
}
