package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.domain.Chat;
import ir.redmind.paasho.domain.User;
import ir.redmind.paasho.repository.ChatRepository;
import ir.redmind.paasho.repository.UserRepository;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.ChatService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.ChatDTO;
import ir.redmind.paasho.service.dto.ChatMinimizeDTO;
import ir.redmind.paasho.service.mapper.ChatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
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
    private final UserService userService;
    private final UserRepository userRepository;

    private final ChatMapper chatMapper;

    @PersistenceContext
    private EntityManager entityManager;


    public ChatServiceImpl(ChatRepository chatRepository, UserService userService, UserRepository userRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
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
        chat.setCreateDate(new Date());
        chat.setFirstRead(false);
        chat.setSecondRead(false);
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
        User user = userService.getUserWithAuthoritiesByLogin(id).get();
        Query q = entityManager.createNativeQuery("select c.first_id,c.second_id from Chat c where c.first_id= ? or c.second_id= ? group by c.first_id,c.second_id ");
        q.setParameter(1, user.getId());
        q.setParameter(2, user.getId());
        List<Object[]> result=q.getResultList();
//        List<Chat> result = chatRepository.searchChats(id);
        Set<BigInteger> usersId = result.stream().map(o->(BigInteger)o[0]).collect(Collectors.toSet());
        usersId.addAll(result.stream().map(o->(BigInteger)o[1]).collect(Collectors.toSet()));
        usersId.remove(user.getId());
        List<User> users=usersId.stream().map(u->userRepository.findById(u.longValue()).get()).collect(Collectors.toList());
        return users.stream().map(u -> new ChatMinimizeDTO(u.getAvatar(), u.getId(), u.getFirstName() + " " + u.getLastName(), false)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatDTO> findAllChatWithUser(Long id, Pageable pageable) {
        log.debug("Request to get all Chats");
        Long myid = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).getId();


        Page<Chat> result = chatRepository.findByFirst_IdOrSecond_Id(myid, id, PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),new Sort(Sort.Direction.DESC,"id")));
        result.getContent().forEach(c->{
            if(c.getFirst().getId().equals(myid))
                c.setFirstRead(true);
            else c.setSecondRead(true);

        });
        chatRepository.saveAll(result.getContent());
        return new PageImpl<>(result.getContent().stream().sorted(Comparator.comparing(Chat::getId))
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
