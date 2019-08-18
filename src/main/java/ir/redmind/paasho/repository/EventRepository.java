package ir.redmind.paasho.repository;

import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.enumeration.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select event from Event event where event.creator.login = ?#{principal.username}")
    List<Event> findByCreatorIsCurrentUser();

    @EntityGraph(attributePaths = {"medias","categories"})
    List<Event> findByStatusAndEventTimeAfterOrderByIdDesc(EventStatus eventStatus,ZonedDateTime dateTime);

    @EntityGraph(attributePaths = {"medias","categories"})
    Page<Event> findByStatusAndEventTimeAfter(EventStatus eventStatus,ZonedDateTime dateTime,Pageable pageable);


    @EntityGraph(attributePaths = {"medias","categories"})
    List<Event> findByStatusAndCreator_Login(EventStatus eventStatus,String login);

    @EntityGraph(attributePaths = {"medias","categories"})
    List<Event> findByStatusAndEventTime(EventStatus eventStatus, ZonedDateTime dateTime);

    @EntityGraph(attributePaths = {"medias","categories"})
    List<Event> findByStatusAndEventTimeIsBetweenOrderByIdDesc(EventStatus eventStatus, ZonedDateTime start,ZonedDateTime end);

    @EntityGraph(attributePaths = {"medias","categories"})
    Page<Event> findByStatusAndEventTimeIsBetweenOrderByIdDesc(EventStatus eventStatus, ZonedDateTime start,ZonedDateTime end,Pageable pageable);

    @Query(value = "select distinct event from Event event left join fetch event.categories left join fetch event.participants",
        countQuery = "select count(distinct event) from Event event")
    Page<Event> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct event from Event event left join fetch event.categories left join fetch event.participants")
    List<Event> findAllWithEagerRelationships();

    @Query("select event from Event event left join fetch event.categories left join fetch event.participants where event.id =:id")
    Optional<Event> findOneWithEagerRelationships(@Param("id") Long id);

    @EntityGraph(attributePaths = {"medias","categories","participants"})
    Event findByCode(String code);

    List<Event> findByTitleIsContainingOrDescriptionContaining(String key,String key2);
    Page<Event> findByTitleIsContainingOrDescriptionContainingOrderByIdDesc(String key, String key2, Pageable pageable);
}
