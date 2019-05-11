package ir.redmind.paasho.repository;

import ir.redmind.paasho.domain.Titles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Titles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TitlesRepository extends JpaRepository<Titles, Long> {

}
