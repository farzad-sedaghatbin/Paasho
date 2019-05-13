package ir.redmind.paasho.repository;

import ir.redmind.paasho.domain.Comment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByEvent_Code(String code);
}
