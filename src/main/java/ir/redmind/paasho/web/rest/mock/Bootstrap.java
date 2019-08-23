package ir.redmind.paasho.web.rest.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Authority;
import ir.redmind.paasho.domain.Contact;
import ir.redmind.paasho.domain.User;
import ir.redmind.paasho.domain.enumeration.ContactType;
import ir.redmind.paasho.repository.AuthorityRepository;
import ir.redmind.paasho.repository.CategoryRepository;
import ir.redmind.paasho.repository.ContactRepository;
import ir.redmind.paasho.repository.UserRepository;
import ir.redmind.paasho.security.AuthoritiesConstants;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.security.jwt.JWTFilter;
import ir.redmind.paasho.security.jwt.TokenProvider;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.mock.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class Bootstrap {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ContactRepository contactRepository;
    private final UserService userService;

    @PersistenceContext
    private EntityManager em;

    public Bootstrap(TokenProvider tokenProvider, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, AuthenticationManager authenticationManager, UserRepository userRepository, CategoryRepository categoryRepository, ContactRepository contactRepository, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
        this.userService = userService;


//        List<User> users = userRepository.findAll();
//        users.parallelStream().forEach(u -> {
//            List<Contact> c = contactRepository.findByUser(u);
//            Contact i=new Contact();
//            if ((u.getTelegram() == null || u.getTelegram().length() == 0 )&&(c!=null && c.size()>0)) {
//               i= c.stream().filter(c1 -> c1.getType().equals(ContactType.TELEGRAM)).findFirst().orElse(new Contact());
//                u.setTelegram(i.getValue());
//            }
//            if ((u.getInstagram() == null || u.getInstagram().length() == 0)&&(c!=null && c.size()>0)) {
//                i = c.stream().filter(c1 -> c1.getType().equals(ContactType.INSTAGRAM)).findFirst().orElse(new Contact());
//                u.setInstagram(i.getValue());
//            }
//            userRepository.save(u);
//
//        });
    }


}
