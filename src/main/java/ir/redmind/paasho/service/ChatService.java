package ir.redmind.paasho.service;

import ir.redmind.paasho.domain.Comment;
import ir.redmind.paasho.service.dto.ChatDTO;
import ir.redmind.paasho.service.dto.ChatMinimizeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Comment.
 */
public interface ChatService {

    /**
     * Save a comment.
     *
     * @param chatDTO the entity to save
     * @return the persisted entity
     */
    ChatDTO save(ChatDTO chatDTO);

    /**
     * Get all the comments.
     *
     * @return the list of entities
     */
    List<ChatDTO> findAll();
    List<ChatMinimizeDTO> chatList(String id);
    Page<ChatDTO> findAllChatWithUser(Long id, Pageable pageable);
    Long unread(String login);



    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ChatDTO> findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the comment corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
}
