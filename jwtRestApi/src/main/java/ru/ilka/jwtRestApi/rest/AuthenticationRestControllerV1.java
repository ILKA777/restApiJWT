package ru.ilka.jwtRestApi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilka.jwtRestApi.dto.AuthenticationRequestDto;
import ru.ilka.jwtRestApi.dto.RegistrationRequestDto;
import ru.ilka.jwtRestApi.model.Role;
import ru.ilka.jwtRestApi.model.User;
import ru.ilka.jwtRestApi.security.jwt.JwtTokenProvider;
import ru.ilka.jwtRestApi.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param requestDto данные для регистрации пользователя
     * @return ResponseEntity с сообщением об успешной регистрации или сообщением об ошибке и кодом состояния HTTP
     */
    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegistrationRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        List<Role> roles = requestDto.toUser().getRoles();

        if (userService.findByUsername(username) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (!isValidEmail(email)) {
            return ResponseEntity.badRequest().body("Invalid email");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRoles(roles);

        userService.register(newUser);

        String message = "User registered successfully";

        return ResponseEntity.ok(message);
    }

    /**
     * Вход пользователя в систему.
     *
     * @param requestDto данные для входа пользователя
     * @return ResponseEntity с данными пользователя и токеном аутентификации или сообщением об ошибке и кодом состояния HTTP
     */
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    /**
     * Проверка корректности email адреса.
     *
     * @param email адрес для проверки
     * @return true, если email адрес корректный, в противном случае - false
     */
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }
}
