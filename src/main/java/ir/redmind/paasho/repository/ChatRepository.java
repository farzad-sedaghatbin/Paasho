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

    @Query("select c.first,c.second from Chat c where (c.first.login=:first and c.second.login=:second) or (c.first.login=:second and c.second.login=:firs) group by c.first,c.second ")
    Page<Chat> findByFirst_IdOrSecond_Id(@Param("first")Long first,@Param("second") Long second, Pageable pageable);

    @Query("select c.first,c.second from Chat c where c.first.login=:first or c.second.login=:first group by c.first,c.second ")
    List<Chat> searchChats(@Param("first") String first);

    @Query("select count(id) from Chat c where ((c.first.login=:first and c.firstRead is false ) or (c.second.login=:first and c.secondRead is false ))  ")
    Long unread(@Param("first") String first);

}
