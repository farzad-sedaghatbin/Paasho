package ir.redmind.paasho.repository;

import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.Media;
import ir.redmind.paasho.domain.enumeration.MediaType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Media entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    void deleteByEvent(Event event);

    Media findByPath(String url);

    List<Media> findByType(MediaType mediaType);

}
