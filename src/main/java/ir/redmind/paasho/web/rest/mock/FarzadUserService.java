package ir.redmind.paasho.web.rest.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/")
public class FarzadUserService {

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

    public FarzadUserService(TokenProvider tokenProvider, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, AuthenticationManager authenticationManager, UserRepository userRepository, CategoryRepository categoryRepository, ContactRepository contactRepository, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
        this.userService = userService;
    }

//    @RequestMapping(value = "user_authenticate", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//            new UsernamePasswordAuthenticationToken(loginDTO.getUsername().toLowerCase(), loginDTO.getPassword());
//        try {
//            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
//            if (authentication.isAuthenticated()) {
//                //todo authenticate
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
////                boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
//                String jwt = tokenProvider.createToken(authentication, true);
//                response.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//                User user = userRepository.findOneByLogin(loginDTO.getUsername().toLowerCase()).orElseThrow( new BadCredentialsException("") );
////                user.setPushSessionKey(loginDTO.getDeviceToken());
//                userRepository.save(user);
//            }
//        } catch (AuthenticationException exception) {
//            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException", exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
//        }
//        return ResponseEntity.ok("401");
//
//    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userDTO, HttpServletResponse response) throws JsonProcessingException {


        User user = userRepository.findOneByLogin(userDTO.getMobile());

        if (user == null) {

            user = new User();
            user.setLogin(userDTO.getMobile());
            user.setActivated(true);
            user.setCreatedBy("system");
            user.setFirstName("کاربر");
            user.setLastName("جدید");
            user.setBirthDate("1360");
            user.setPoint(0d);
            user.setPassword(passwordEncoder.encode("123"));
            userRepository.save(user);

            Set<Authority> authorities = new HashSet<>();
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.USER);
            authorities.add(authorityRepository.findOne(Example.of(authority)).get());
            user.setAuthorities(authorities);
            userRepository.save(user);

        }
        int START = 1000;
        int END = 9999;
        Random random = new Random();
        long range = END - START + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * random.nextDouble());
        int randomNumber = (int) (fraction + START);
        String s = String.valueOf(randomNumber);

        user.setResetKey(s);
//            user.setResetDate();
        userRepository.save(user);
        try {
            String tel = user.getLogin();

            KavenegarApi api = new KavenegarApi("6F442B597454543263327452344D3876636C7443735034476B7170577571376F");
//                api.send("10006006606600", tel, "شماره بازیابی :  " + s);

            api.verifyLookup(tel, s, "pashosignup");

        } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("HttpException  : " + ex.getMessage());
            return ResponseEntity.ok("302");
        } catch (ApiException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("ApiException : " + ex.getMessage());
            return ResponseEntity.ok("302");
        }
//            MailUtils.sendEmail("farzad.sedaghatbin@gmail.com", s, "ResetPassword");

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "changeAvatar", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> changeAvatar(@Valid @RequestParam("avatar") String data) {

//        String[] s = data.split(",");
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        if (user.getAvatar() == null || user.getAvatar().length() == 0) {

            user.setPoint(user.getPoint() + 10);
        }
        user.setAvatar(data);
        userRepository.save(user);
        return ResponseEntity.ok("200");
    }


//    @RequestMapping(value = "videoLimit", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> videoLimit(@Valid @RequestBody String data) {
//
//        User user = userRepository.findOneByLogin(data.toLowerCase());
//        if (user.getLastVideo() == null || (user.getLastVideo() != null && ((ZonedDateTime.now().toInstant().toEpochMilli() - user.getLastVideo().toInstant().toEpochMilli()) / 360000) >= 1)) {
//            user.setLastVideo(ZonedDateTime.now());
//            userRepository.save(user);
//            return ResponseEntity.ok("200");
//        } else {
//            return ResponseEntity.ok("201");
//        }
//    }

//    @RequestMapping(value = "forget", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> forget(@Valid @RequestBody String username) {
//        //todo forget scenario send email or sms
//        User user = userRepository.findOneByLogin(username.toLowerCase());
//        if (user != null) {
//
//            int START = 1000;
//            int END = 9999;
//            Random random = new Random();
//            long range = END - START + 1;
//            // compute a fraction of the range, 0 <= frac < range
//            long fraction = (long) (range * random.nextDouble());
//            int randomNumber = (int) (fraction + START);
//            String s = String.valueOf(randomNumber);
//            User user = user;
//            user.setResetKey(s);
////            user.setResetDate();
//            userRepository.save(user);
//            try {
//                String tel = user.getMobile();
//
//                KavenegarApi api = new KavenegarApi("5635717141617A52534F636F49546D38454E647870773D3D");
////                api.send("10006006606600", tel, "شماره بازیابی :  " + s);
//
//                api.verifyLookup(tel, s, "dagalareset");
//
//            } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
//                System.out.print("HttpException  : " + ex.getMessage());
//                return ResponseEntity.ok("302");
//            } catch (ApiException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
//                System.out.print("ApiException : " + ex.getMessage());
//                return ResponseEntity.ok("302");
//            }
////            MailUtils.sendEmail("farzad.sedaghatbin@gmail.com", s, "ResetPassword");
//            return ResponseEntity.ok("200");
//        } else {
//            return ResponseEntity.ok("201");
//        }
//    }


    @RequestMapping(value = "confirmOTP", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> confirmOTP(@Valid @RequestBody ConfirmOTPDTO data, HttpServletResponse response) {
        //todo forget scenario send email or sms

        Optional<User> user = userRepository.findOneByResetKeyAndLogin(data.getCode(), data.getMobile());
        if (user.isPresent()) {
            User usr = user.get();
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(data.getMobile(), "123");
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, true);
            response.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            System.out.println(jwt);
            usr.setPassword(passwordEncoder.encode("123"));
            usr.setResetDate(ZonedDateTime.now().toInstant());
            userRepository.save(usr);
            return ResponseEntity.ok("200");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "inviteFriend", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<HttpStatus> inviteFriend(@RequestBody String data) {
        try {
            Long id = Long.valueOf(data.substring(4, data.length() - 1));
            id = (id - 5) / 10;
            Optional<User> u = userRepository.findById(id);
            if (u.isPresent()) {
                User user = u.get();
                user.setPoint(user.getPoint() + 50);
                userRepository.save(user);
            } else {
                return ResponseEntity.notFound().build();
            }


            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }
    }
/*    @RequestMapping(value = "confirmReset", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> confirmReset(@Valid @RequestBody ConfirmOTPDTO data) {
        //todo forget scenario send email or sms

        Optional<User> user = userRepository.findOneByResetKey(data.getCode());
        if (user.isPresent()) {
            User user = user.get();
            user.setPassword(passwordEncoder.encode(data.getMobile()));
            user.setResetDate(ZonedDateTime.now());
            userRepository.save(user);
            return ResponseEntity.ok("200");
        } else {
            return ResponseEntity.ok("201");
        }
    }*/


//    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> changePassword(@Valid @RequestBody String data) {
//        String[] s = data.split(",");
//        User user = userRepository.findOneByLogin(s[0].toLowerCase());
//        user.setMobile(passwordEncoder.encode(s[1]));
//        userRepository.save(user);
//        return ResponseEntity.ok("200");
//    }
//
//    @RequestMapping(value = "/2/changePassword", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> changePasswordV2(@Valid @RequestBody String data) {
//        String[] s = data.split(",");
//        User user = userRepository.findOneByLogin(s[0].toLowerCase());
//        if (user.getMobile().equalsIgnoreCase(passwordEncoder.encode(s[2]))) {
//            user.setMobile(passwordEncoder.encode(s[1]));
//            userRepository.save(user);
//            return ResponseEntity.ok("200");
//        } else {
//            return ResponseEntity.ok("201");
//        }
//    }


    @RequestMapping(value = "profile", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> setProfile(@RequestBody ProfileDTO profileDTO) throws JsonProcessingException {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        user.setFirstName(profileDTO.getFirstName());
        user.setLastName(profileDTO.getLastName());
        user.setEmail(profileDTO.getEmail());
        user.setBirthDate(String.valueOf(profileDTO.getBirthYear()));
        user.setGender(profileDTO.getGender());
        Contact contact = new Contact();
        contact.setType(ContactType.INSTAGRAM);
        contact.setUser(user);
        contact.setValue(profileDTO.getInstagram());

        Contact contact1 = new Contact();
        contact1.setType(ContactType.TELEGRAM);
        contact1.setUser(user);
        contact1.setValue(profileDTO.getTelegram());

        contactRepository.save(contact);
        contactRepository.save(contact1);
        user.getContacts().add(contact);
        user.getContacts().add(contact1);
        userRepository.save(user);
        return ResponseEntity.ok(profileDTO);

    }

    @GetMapping(value = "profile-by-Id/{userId}")
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> getProfileById(@PathVariable("userId") String UserId) throws JsonProcessingException {
        ProfileDTO profileDTO = new ProfileDTO();
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());

        profileDTO.setAvatar(user.getAvatar());
        userToProfile(profileDTO, user);

        return ResponseEntity.ok(profileDTO);

    }

    private void userToProfile(ProfileDTO profileDTO, User user) {
        profileDTO.setBirthYear(Integer.parseInt(user.getBirthDate()));
        profileDTO.setEmail(user.getEmail());
        profileDTO.setFirstName(user.getFirstName());
        profileDTO.setGender(user.getGender());
        if (user.getContacts().size() > 0) {
            profileDTO.setTelegram(user.getContacts().stream().filter(c -> c.getType().equals(ContactType.TELEGRAM)).findFirst().get().getValue());
            profileDTO.setInstagram(user.getContacts().stream().filter(c -> c.getType().equals(ContactType.INSTAGRAM)).findFirst().get().getValue());
        }
        profileDTO.setLastName(user.getLastName());
        profileDTO.setAvatar(user.getAvatar());
        profileDTO.setBirthYear(Integer.parseInt(user.getBirthDate()));
        profileDTO.setGender(user.getGender());
        profileDTO.setCode("psh" + (user.getId() * 10 + 5));
    }


    @RequestMapping(value = "profile", method = RequestMethod.GET)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<ProfileDTO> getProfile() throws JsonProcessingException {
        ProfileDTO profileDTO = new ProfileDTO();
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        userToProfile(profileDTO, user);
        return ResponseEntity.ok(profileDTO);

    }


    @RequestMapping(value = "category", method = RequestMethod.GET)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<List<CategoryDTO>> category() throws JsonProcessingException {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());

        List<CategoryDTO> cat = new ArrayList<>();
        CategoryDTO cat1 = new CategoryDTO();
        cat1.setIcon("http://cdn.persiangig.com/preview/UFsDYQLkdP/gathering.png");
        cat1.setId(1);
        cat1.setName("دور همی");
        cat1.setSelected(false);
        cat.add(cat1);
        CategoryDTO cat2 = new CategoryDTO();
        cat2.setIcon("http://cdn.persiangig.com/preview/pBiqZHeQGg/business.png");
        cat2.setId(2);
        cat2.setName("کسب و کار");
        cat2.setSelected(false);
        cat.add(cat2);
        CategoryDTO cat3 = new CategoryDTO();
        cat3.setIcon("http://cdn.persiangig.com/preview/JGP9ghOgwF/art.png");
        cat3.setId(3);
        cat3.setName("فرهنگی و هنری");
        cat3.setSelected(false);
        cat.add(cat3);
        CategoryDTO cat4 = new CategoryDTO();
        cat4.setIcon("http://cdn.persiangig.com/preview/keubySi0Yh/food.png");
        cat4.setId(4);
        cat4.setName("رستوران و کافی شاپ");
        cat4.setSelected(false);
        cat.add(cat4);
        CategoryDTO cat5 = new CategoryDTO();
        cat5.setIcon("http://cdn.persiangig.com/preview/cIcbK7N4QI/sport.png");
        cat5.setId(5);
        cat5.setName("ورزشی");
        cat5.setSelected(false);
        cat.add(cat5);
        CategoryDTO cat6 = new CategoryDTO();
        cat6.setIcon("http://cdn.persiangig.com/preview/87sWQgMaoD/education.png");
        cat6.setId(6);
        cat6.setName("آموزشی");
        cat6.setSelected(false);
        cat.add(cat6);
        CategoryDTO cat7 = new CategoryDTO();
        cat7.setIcon("http://cdn.persiangig.com/preview/a8x9hwNWCK/charity.jpg");
        cat7.setId(7);
        cat7.setName("خیریه و افتتاحیه");
        cat7.setSelected(false);
        cat.add(cat7);
        CategoryDTO cat8 = new CategoryDTO();
        cat8.setIcon("http://cdn.persiangig.com/preview/yhYdUfsSIL/family.png");
        cat8.setId(8);
        cat8.setName("سلامت و خانواده");
        cat8.setSelected(false);
        cat.add(cat8);
        CategoryDTO cat9 = new CategoryDTO();
        cat9.setIcon("http://cdn.persiangig.com/preview/pX5gnPxWTk/fashion.jpg");
        cat9.setId(9);
        cat9.setName("مد و زیبایی");
        cat9.setSelected(false);
        cat.add(cat9);
        return ResponseEntity.ok(cat);

    }


    @PostMapping(value = "category/favourite")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> getTitles(@RequestBody List<Long> ids) {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());

        user.setFavorits(new HashSet<>(categoryRepository.findAllById(ids)));
        userRepository.save(user);
        return ResponseEntity.ok("200");

    }


    @RequestMapping(value = "ranks", method = RequestMethod.GET)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<RecordDTO> topUsers(HttpServletResponse response) throws JsonProcessingException {
        Page<User> users = userRepository.findAll(new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "point")));

        RecordDTO recordDTOS = new RecordDTO();
        recordDTOS.setHelp("با شرکت کردن در هر رویداد 10 امتیاز به شما تعلق میگیرد\n" + "با ایجاد کردن رویداد جدید به ازای هر شرکت کننده 10 امتیاز دریافت میکنید\n" + "در ازای دعوت کردن از هر یک از دوستان خود 50 امتیاز دریافت خواهید کرد\n");
        recordDTOS.setPrizeDescription("هر هفته 200000 تومان به نفر اول پرداخت می شود");
        recordDTOS.users = new ArrayList<>();
        List<RecordDTO.User> userList = recordDTOS.users;
        int i = 1;

        for (User objects : users.getContent()) {
            RecordDTO.User user = new RecordDTO.User();
            user.setUser(objects.getFirstName() + " " + objects.getLastName());
            int s = objects.getPoint().intValue();

            user.setScore(s);
            user.setIndex(i++);
            user.setAvatar(objects.getAvatar());
            userList.add(user);

        }
        try {


            Query q = em.createNativeQuery("SELECT * FROM (SELECT id,rank() OVER (ORDER BY point DESC) FROM jhi_user ) as gr WHERE  id =?");
            User u = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
            q.setParameter(1, u.getId());
            Object[] o = (Object[]) q.getSingleResult();
            recordDTOS.rank = ((BigInteger) o[1]).bitCount();
            recordDTOS.score = u.getScore().intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        recordDTOS.users = userList;
        return ResponseEntity.ok(recordDTOS);

    }


}
