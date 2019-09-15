package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.domain.Chat;
import ir.redmind.paasho.domain.User;
import ir.redmind.paasho.repository.ChatRepository;
import ir.redmind.paasho.repository.UserRepository;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.ChatService;
import ir.redmind.paasho.service.dto.ChatDTO;
import ir.redmind.paasho.service.dto.ChatMinimizeDTO;
import ir.redmind.paasho.service.mapper.ChatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Chat.
 */
@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;
    private final UserRepository userRepository;


    public ChatServiceImpl(ChatRepository chatRepository, ChatMapper chatMapper, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a chat.
     *
     * @param chatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChatDTO save(ChatDTO chatDTO) {
        log.debug("Request to save Chat : {}", chatDTO);
        Chat chat = chatMapper.toEntity(chatDTO);
        chat.setFirst(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()));
        chat = chatRepository.save(chat);
        ChatDTO result = chatMapper.toDto(chat);
        return result;
    }

    /**
     * Get all the chats.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChatDTO> findAll() {
        log.debug("Request to get all Chats");
        return chatRepository.findAll().stream()
            .map(chatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMinimizeDTO> chatList(String id) {
        log.debug("Request to get all Chats");
        List<Chat> result = chatRepository.searchChats(id);
        Set<User> users = result.stream().map(Chat::getFirst).collect(Collectors.toSet());
        users.addAll(result.stream().map(Chat::getSecond).collect(Collectors.toSet()));
        return users.stream().map(u -> new ChatMinimizeDTO(u.getAvatar(), u.getId(), u.getFirstName() + " " + u.getLastName(), false)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatDTO> findAllChatWithUser(Long id, Pageable pageable) {
        log.debug("Request to get all Chats");
        Page<Chat> result = chatRepository.findByFirst_IdOrSecond_Id(id, id, pageable);
        return new PageImpl<>(result.getContent().stream()
            .map(chatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new)), result.getPageable(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Long unread(String login) {
        log.debug("Request to get all Chats");
        return chatRepository.unread(login);
    }

    /**
     * Get one chat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChatDTO> findOne(Long id) {
        log.debug("Request to get Chat : {}", id);
        return chatRepository.findById(id)
            .map(chatMapper::toDto);
    }

    /**
     * Delete the chat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chat : {}", id);
        chatRepository.deleteById(id);
    }

    /**
     * Search for the chat corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
}
