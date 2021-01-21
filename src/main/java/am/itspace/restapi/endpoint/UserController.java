package am.itspace.restapi.endpoint;

import am.itspace.restapi.dto.AuthRequest;
import am.itspace.restapi.dto.AuthRespanse;
import am.itspace.restapi.dto.UserDto;
import am.itspace.restapi.exception.DuplicateEntityException;
import am.itspace.restapi.model.User;
import am.itspace.restapi.service.UserService;
import am.itspace.restapi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/user/add")
    public User createUser(@RequestBody UserDto userDto) {
        if(userService.findByEmail(userDto.getEmail()).isEmpty()) {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            return userService.save(user);
        }
        throw new DuplicateEntityException("User already exists");
    }

    @PostMapping("/user/auth")
    public ResponseEntity auth(@RequestBody AuthRequest authRequest) {
        Optional<User> byEmail = userService.findByEmail(authRequest.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                String token = jwtTokenUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(AuthRespanse.builder()
                        .token(token)
                        .name(user.getName())
                        .surname(user.getSurname())
                        .build());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

}
