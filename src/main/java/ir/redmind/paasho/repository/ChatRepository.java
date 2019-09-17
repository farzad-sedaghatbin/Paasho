package ir.redmind.paasho.repository;

import ir.redmind.paasho.domain.Chat;
import ir.redmind.paasho.domain.Comment;
import ir.redmind.paasho.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat c where (c.first.id=:first and c.second.id=:second) or (c.first.id=:second and c.second.id=:first) ")
    Page<Chat> findByFirst_IdOrSecond_Id(@Param("first")Long first,@Param("second") Long second, Pageable pageable);

    @Query("select c from Chat c where c.first.login=:first or c.second.login=:first ")
    List<Chat> searchChats(@Param("first") String first);

    @Query("select count(id) from Chat c where ((c.first.login=:first and c.firstRead is false ) or (c.second.login=:first and c.secondRead is false ))  ")
    Long unread(@Param("first") String first);

}
