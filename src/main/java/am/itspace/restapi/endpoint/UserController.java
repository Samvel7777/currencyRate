package am.itspace.restapi.endpoint;

import am.itspace.restapi.dto.AuthRequest;
import am.itspace.restapi.dto.AuthRespanse;
import am.itspace.restapi.dto.UserDto;
import am.itspace.restapi.exception.DuplicateEntityException;
import am.itspace.restapi.model.LastLogin;
import am.itspace.restapi.model.User;
import am.itspace.restapi.security.CurrentUser;
import am.itspace.restapi.service.EmailService;
import am.itspace.restapi.service.LastLoginService;
import am.itspace.restapi.service.UserService;
import am.itspace.restapi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final LastLoginService lastLoginService;
    private final EmailService emailService;

    @PostMapping("/user/add")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        if (userService.findByEmail(userDto.getEmail()).isEmpty()) {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setActive(false);
            user.setToken(UUID.randomUUID().toString());
            userService.save(user);
            String link = "http://localhost:8080/activate?email=" + user.getEmail() + "&token=" + user.getToken();
            emailService.send(user.getEmail(), "Dear " + user.getEmail(), "Please click to this link to activate your page " + link);
            return ResponseEntity.ok().build();
        }
        throw new DuplicateEntityException("User already exists");
    }

    @GetMapping("/activate")
    public ResponseEntity<User> activate(@RequestParam("email") String email, @RequestParam("token") String token) {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getToken().equals(token)) {
                user.setActive(true);
                user.setToken("");
                userService.save(user);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/user/auth")
    public ResponseEntity auth(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        Optional<User> byEmail = userService.findByEmail(authRequest.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                String token = jwtTokenUtil.generateToken(user.getEmail());
                lastLoginService.lastLoginApi(request, user.getId());
                return ResponseEntity.ok(AuthRespanse.builder()
                        .token(token)
                        .name(user.getName())
                        .surname(user.getSurname())
                        .build());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @GetMapping("/user/lastLogin")
    public ResponseEntity<List<LastLogin>> lastLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            return ResponseEntity.ok(lastLoginService.findByUserId(user.getId()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
