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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
@GetMapping(value = "ranks")
@Timed
@CrossOrigin(origins = "*")

public ResponseEntity<RecordDTO> ranks() {

    RecordDTO recordDTOS= new RecordDTO();
    recordDTOS.setHelp("دعوت از دوستان 5 امتیاز\n" +
        "ایجاد رویداد به ازای هر شرکت کننده 1 امتیاز \n" +
        "شرکت در هر رویداد 1 امتیاز");
    recordDTOS.setPrizeDescription("جایزه پرداخت میشه نفر اول 200000 تومان هر هفته");
    recordDTOS.rank=2;
    recordDTOS.score=1200;
    recordDTOS.users= new ArrayList<>();
    RecordDTO.User u= new RecordDTO.User();
    u.setAvatar("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");
    u.index=2;
    u.score=1300;
    u.user="farzad sedaghatbin";
    RecordDTO.User u2= new RecordDTO.User();
    u2.setAvatar("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");

    u2.index=1;
    u2.score=1300;
    u2.user="ali sedaghatbin";
    RecordDTO.User u3= new RecordDTO.User();
    u3.setAvatar("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");
    u3.index=3;
    u3.score=1300;
    u3.user="nazanin sedaghatbin";
    RecordDTO.User u4= new RecordDTO.User();
    u4.setAvatar("iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAABcVBMVEX17uUknPLyzqXmwZzmpCJFIihrNj7UsIzZjCGjcF+7hmBZLDMmJibMmHK5g11oLztlMD/mv5f28evrqCD58OUAl/NiLD/x49T406loMz7lnwBoLTPloRDtw5fdjx/79evXhQCuczAACBVzPTzZtZDz0KvprkfuypPEhiufZjTcmyXioCOdaFlAGiHyzKGnZjDsw3/SwbynbTLz17i60eTv0aEVGh42ABF4Qjvl29Q5ER9MJiyoyePjuZE5n+vt2MFjRkhkruvTpn/g4+DRhiSXsMaCTDnQkShfKUBgICy6fi3nqjXXvqSJYmXr49p+t+W1m35rXlCms76+r6ovAACMVTftvXXho1VtqNiKdnXHn4SIWlV3R02skZDIpoeYeGWwgGuPamzLq5eHZ1htTUU0BBrKu6rN2eGyzeRXqeu8t7WEq9JWNDSTcmDsuGbluYTrvnDdm0SVXTZzZFRBPDZVTEJlSUuZhYN2XV7PpGnFik/XquEXAAAO80lEQVR4nO2d+1vTSBfHQ9MW5GKbtPRmW0Dacm0XBLnfQSoXURHQZbHwviIsXmBF3XXfv/6dmUySya1JMc0kPvn+sgtNO/Pp98w5ZybhkWF8+fLly5cvX758+fLly5cvX758+fLlWvE8T3sKTRU/cT0wkYzz/K8LOpHJZoI7pb8GiiKogWhP9K7ilzNBqGw2k81mAer1X0djUEfXpZ2dIPwd4n+8DOOZ9mzvpKRAKCkrSfnLTLY0thz3JGQsaFXZzM7YsvcY+eusORthZqnoNUb+ccYcjFRmZ8JjiPEdfZKYKC3jddJDjKAMjGW1aCPV6uB2H9T2YLU6ogLNBh97BZHnJ8Z2VHgjg48O+8PpaCIqKh3uP3w0OEJCZsY8gcgnB3YypIGAbmjmaQIgqQWAEzOQUo7UOO3pm4pPjgUVeMHB2349OoKy/3YwiCGzJdoAJuIZFd9I30w0akgnKhqd6cNGZq9dHah8Uck3PBSt457SyejQMGLMHrkXkY9fZ9R8SgzAAVYeEkw1SniRMePajMov7xAGxoJ94ajCpER05nAIlYkqKhpDhzNR5QKNhvvQekzSRtEXX1QEaHWG4EsnEpt9gyNBudQL/wdKyKYCMjpTjYFs40oT+YEMaeDQ07TsXvgQ5Er9VhwUyu3DsAyZfjoUjGXc2MAp+lDSwHRipm/YAE+EHO6bSaRJG0vua8OVgNtSDkknNqVSVw8yOLgpMabT27EjdtldO2N+gswxQwnZj8G69pFGDsq+J4byvb1tRxMuYkwScx05FAGj/dsW+QTG7X6RMXrY1taW762yjEsYye3uyKY4zcTtsHU+xDh8K305m21QvdWAKxAVi1CMNbiaGuJDjNIKTs+0CYyv3bBpJGJ0RASMbjZooGijGAIiYr63SB2RP5JjVJxf4vYOeALiYVQRqMDGHG3EpAQYE2eXeHQXA6GG2/JDIuJhHiPe0EXk/xIJY49wokj03RUQMuV/ExGHRESWKqJ09hvbFgHvkGNkQBKxT0RcpgjID4gWVsN2OKhADG/g33yhaCIvHTltpnFDojg/057lG2mkTVL+VkBMS9mGXkKV+jVxEUZvZcBscOd6bKBSGRg7KgXNIAlAgIhzVvQ37GuVHqF4LFp9isuYzFc6qhSLxQBUsVi5rg843KbUDI4IHKe99DZUJWyhGKPDEt9YQKDDKg6U6tiYVwHmN8Q4xdnnNS1CXAxj21FVlhkrKviQjO/WqAHlbCPlU0pHG2JLOtKPLEwf4iPBUkXLB2wcM1iNEmBeRs3jqOinG6Z4Gcb6hDSTFmI0e63HBxErOjZmR14f3eTAi4FK7ubo9UYvRtwIk8mGWmMjzHgET+YRPvE0AESrcUfJmA0eKfwuVm5e46Untm94IdIBjAdJC8NBM0Do1FhJLo/Z7LVOPOe+CD4qTKzSIRRatlg/0a0Zhijh41EJ9Qk7pSPd9Roo3pDJpl8IUyr3bPgJSBgbFCzsR7aUTPgQQbEyAFTRybf4ghwyUfjmotsQkU5vyj+G8RY7TAt5Ha3CigVCC9/BTa9kolAT6SRToe0exicPIzBGx8xi1CoizDcbRANOpzVFhLG+qLCbgxaWbAIMBCqoA09Luaa3Qs9DoTRHq9DCAbsAA8WjfFt+OyptMXo5aoTDwixmYhbTjFXBZJMXck16g9ZxDSTEW3tU7euXwgZV/CJVfdicUiQU1kqial8ixboBqw+H6WGeYpTich+1N89A5QBhXir6lDINqIfDT6Vdha1BCsJU3mGAjTClajGRwQ0NKvc2ZlJE+EUq+qCt6Z2gAAgflMXVMDEIl6GtgKBetIn1AlTEXjpb4GRGTDRwZ7hja5AKqWYjgVMNnc4b7J7wAQ3quk13FY0S9ordd3om30bpebBSDB8YmW4M76Bcr3zoRmsHzF8L2/s0PCW1reuWBPcXwsYl3PYvrXMaYWOB2m6bUymQ3HyHNyid0/CPMeGjJhOmNyid6/PLQtMhENras0FV5V3wBq3bT8lqVC74dgMG5JKf3qb1sFv8dcQRwsj/KAEy/L8yYcx2wmpeIuyidd+CL4iEw4PbmhlWOCiT5ZkKcNziGtAiV0kpX/ptewMTPtilRnjxQKj4m+HElmJ2i2tdksDkDfEWu9rb72O1t69xJGQkGt4UeqYH45QAGWYcEHbMhzrAPB4Q5hF4WIt6gJU1xCUL/ERcCD473RGa76BNOB8KhTrCJKGWT59xTYmHIbtYgjDcAT59Ph2hRtgzXg6FVIQVXT4YrCoD2++LvrWDV1Gwop/bORVhKFQe76EEOLuiIZQAF3GO4eQlSQJyCPD+/S5pkXKLXZhxUUO4MksFETgoABKEGHBNmVtESBIQsayxZGpJVdaEX3MqwlCZios949OhkJpQQNHmzkVVoOJ4VNWHQApZe79LTQgQKbg4O1/WEHIIRK8EciR6ag1x6JZKVDzQdRGCMFSe33OccEUCJKN0Uc9B9AqwUESqoPWmexmIXzHtKghD5b8d5uv5R4pRVbUgAcko5OQXkIVG10k/KAlD0/84G6ezByEDQmKmrTVWvdKQoINd8ispttYa0F6oIgxNzzoJ2POjbEaYqn2bmur8qgPIESUB6mvn1NS3mgZRTVj+4aSJ40SM6hOmXjzsBJr6rkOoDNLvU/DChy/UiGrC0LSDrU3P72UzwsC3zk79mSsJ8TfR2fnNzMNQ+XfnTJwNhdSEEdX8CnjiU19NCL9O4a+iYEYYmndsJfa8mjYlbBUJX5oQvhQJW00Jp185ZaIqSHUJWTzxqVMTwlPxQlZ1mZbQwTBdMScUo29KHXzqTFOYMohmHULHqv6eMkh1CUGOfDjVOaWTaFSEINXA67Q5V0sYmnOqdds7sEKYevHy+6leyVdVixR7+v2lzhehQ3jgFOGsJQ8DKSDtbzWERhfqEDpWEcetERpJQ6gvXUKHUs2vT2gxSptA6FTJt5ZpmkDoWKaxVi2aQDjt2EbfQsVvBqFzFd9K19YUQse6NiuddzMIneu8reyemkEYcu4cQ3mI4RShkztgnVMMBwidPMVgev5Wn0Q1RCge+jZE6OxJlPY0sQHCAKfe7FoiPHD0NFF7ItwIoTVRPhHWnOo3m7C84jAf0zM75yyhszGKEMcPHCQ8oHMDkbxD2lRCOndIIeJ82RHC8jydu9wAce/HgQOEBz/2KAFCxlfz0+UmE5ad67d1EZm3K9OQMN0kwvmOE5p8AuP4PydbW1vmU25Q4DOfnXSEI89oEwLGHmZv/JXthK/G95hnIPwd72UMxNv9IDt88HkPROoDusuQ0ITNhPDvgMa3wuEteo/tqbRs84PeyyD838Js4/yDNAZK2ktYSQJCWIWeuSVIGUafkL1nJv3NYiUONqFbblqGDKOfagqjJoCj2luoSHAPCoJ0y/k9haEMUs2ZCeCx/ttAopl1WZAaLsT6hGf6bwLLEOWZLVoPz+rKoCLWW4mjBoCBQJyZhYmUestGil/Wn2uq0HCIwiDt+Y/rLGSSRtNlzwzSTavuXXAUpD2vtsKu6EkVMm5rju9pGEfvGRoIWrYe2M6EIy5KpEjGRT8VOD4bJSBHR8+O9Z/MxBbOngDArbeuilGoCeO+JhVga5AS6t5ZjdV5oJSwcO8k4rJKIar+BgM+VMKywn/rXjcbhoAnrulICRkmm0aU+i/8a6dImNbxU30ZVIxG+AKnERcDMknze0oapBTxTFQqVTtxNSCTbLV0X0mmCxROz+fmzoXKmAq0/oH+pHHrGcUDxPpKtlpGBMYVXrz79H6uHAqV39egj8d/RATAt7Q5jAUIWw22Q2rzaqcr5TnxBlb5nK3dO4mgP6jcOnFXr6YUJDRHBOadl+fmFA8DRAT7wpHIW8bFgAKhaaS+e6+kE28NwAzzuebSf4cFCxPWRUydz6nxgOahfSdntUKrNwjrRGqq9l4HEIRpePS4tQDe6nLCRRHR0MbUZ02EQs2dFQrC+1xOyLKtJjam3ukFaWjuFAO6nZAzRUy9qENYYFnO/YQyon6kFvSjtCYAeoKQLdS1MfVJD/BzQQD0BiGBqGOjJtWA4v/pFDvoFUISsaBmTB3PKfHOT2uwTAjv9AohuRi1oTon0ZU/fX7RKpQJ1muEJKKKMVUDPSkMzXenNVwEcYR6i5CMVERAIBY+n5+fHrcWCsTLHiRUIiIOAaQgSMnvSUJlpNaR4j3eIrSEWGC9TKiNVDNAzxGaIqqvd3XnnVxmNYAmkao2EJnILrsOMs4DuqIuXl0b9fgwZBFQ8u74V6zj8fj65YTRVOsxGvKJmrhcB59Om465fL7U3W3kHgmppCyY8gEnu7uXnl8y1Cgh3cJqS3d3S0v3vgVEzFmwwiYA7sOP7m5ZXYCUjtPF1yeheS2ClqwSNiJuCX86tHLSyYAFQ4HQbBHp0Bwsm9gA4L5ihBYQsE5AgiHWF1a7u8nBkYm2A7LskmoMMOrqwnozA1YdmorRP+Zs5st91B2neQELQ3PhqkVvVGHoC3vjlLswGgp8w1cLNgcsNO9PIWsay+44rTcWyrB/2mQlKugGoanUla2AV6bjoWL5s5QoNM3Mk0Z8Y99SzL2xNiQqlneFBO/bm1QVBacQLQIKo4IyMrnXMKXYjVkeByPak224BgCFgXF3Z51u3XJoqkZa3f15Rm539S5Dw4C1UiwbDk3VOEsXPxupuYulO4+OA7Yu3+Tqnemw9n8OMbf/c8MDKycNGePMwp2/PmKI1d27M+buFKHqGSwtMLqM8Ukb+JCeGO7264tjn9gzge6lSy1inHluEx8cYD/XOCOX27frKwZTeK62Mb5u36fDAZY+cI3Fao6zkQ/NYF2BGJ+089OFEZ7sWjaSy+0+sZUPiYxU+wFbYFZ784G1AAnM/vDmZzO4rmTE+GUzPr8FQe6zuTqUXC7H7jcHD0pEjK83a4QW1FB93N+FKBwnk4L/h+C7+x8bbg0b0jr2UH1cYLcA5dWbJ/sfLi52BV1cfNh/8uaquXRQV8jEuH1lop66hcOdpaUl+YfmD7oQb+IidIdAzYib76a9rNU404xC4SJ1XzK/toXARIb2DJoun9D78gm9L5/Q+/IJvS+f0PvyCb0vn9D78gm9L5/Q+/IJva9fn/D/ed/MWR+ASAgAAAAASUVORK5CYII=");
    u4.index=4;
    u4.score=1300;
    u4.user="farshad sedaghatbin";
    RecordDTO.User u5= new RecordDTO.User();
    u5.setAvatar("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");
    u5.index=5;
    u5.score=1300;
    u5.user="mohamad sedaghatbin";
    recordDTOS.users.add(u);
    recordDTOS.users.add(u2);
    recordDTOS.users.add(u3);
    recordDTOS.users.add(u4);
    recordDTOS.users.add(u5);
    return  ResponseEntity.ok(recordDTOS);

}
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
            user.setBirthDate("0");
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
//            user1.setResetDate();
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

    public ResponseEntity<?> changeAvatar(@Valid @RequestBody String data) {

//        String[] s = data.split(",");
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());

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
//            User user1 = user;
//            user1.setResetKey(s);
////            user1.setResetDate();
//            userRepository.save(user1);
//            try {
//                String tel = user1.getMobile();
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
            User user1 = user.get();
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(data.getMobile(), "123");
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, true);
            response.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            System.out.println(jwt);
            user1.setPassword(passwordEncoder.encode("123"));
            user1.setResetDate(ZonedDateTime.now().toInstant());
            userRepository.save(user1);
            return ResponseEntity.ok("200");
        } else {
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
            User user1 = user.get();
            user1.setPassword(passwordEncoder.encode(data.getMobile()));
            user1.setResetDate(ZonedDateTime.now());
            userRepository.save(user1);
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
//        User user1 = userRepository.findOneByLogin(s[0].toLowerCase());
//        user1.setMobile(passwordEncoder.encode(s[1]));
//        userRepository.save(user1);
//        return ResponseEntity.ok("200");
//    }
//
//    @RequestMapping(value = "/2/changePassword", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> changePasswordV2(@Valid @RequestBody String data) {
//        String[] s = data.split(",");
//        User user1 = userRepository.findOneByLogin(s[0].toLowerCase());
//        if (user1.getMobile().equalsIgnoreCase(passwordEncoder.encode(s[2]))) {
//            user1.setMobile(passwordEncoder.encode(s[1]));
//            userRepository.save(user1);
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
//        profileDTO.setBirthYear(Integer.parseInt(user.getBirthDate()));
        profileDTO.setEmail(user.getEmail());
        profileDTO.setFirstName(user.getFirstName());
        profileDTO.setGender(user.getGender());
       // profileDTO.setTelegram(user.getContacts().stream().filter(c -> c.getType().equals(ContactType.TELEGRAM)).findFirst().get().getValue());
        //profileDTO.setInstagram(user.getContacts().stream().filter(c -> c.getType().equals(ContactType.INSTAGRAM)).findFirst().get().getValue());
        profileDTO.setLastName(user.getLastName());
        profileDTO.setAvatar(user.getAvatar());
        profileDTO.setBirthYear(Integer.parseInt(user.getBirthDate()));
        profileDTO.setGender(user.getGender());
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

}
