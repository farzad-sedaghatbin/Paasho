package ir.redmind.paasho.repository;

import ir.redmind.paasho.domain.User1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the User1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface User1Repository extends JpaRepository<User1, Long> {

    @Query(value = "select distinct user_1 from User1 user_1 left join fetch user_1.favorits",
        countQuery = "select count(distinct user_1) from User1 user_1")
    Page<User1> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct user_1 from User1 user_1 left join fetch user_1.favorits")
    List<User1> findAllWithEagerRelationships();

    @Query("select user_1 from User1 user_1 left join fetch user_1.favorits where user_1.id =:id")
    Optional<User1> findOneWithEagerRelationships(@Param("id") Long id);

}
