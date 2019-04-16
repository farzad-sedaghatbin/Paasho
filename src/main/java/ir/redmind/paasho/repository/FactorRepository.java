package ir.redmind.paasho.repository;

import ir.redmind.paasho.domain.Factor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Factor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactorRepository extends JpaRepository<Factor, Long> {

}
