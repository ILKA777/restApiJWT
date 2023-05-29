package ru.ilka.jwtRestApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.ilka.jwtRestApi.model.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequestDto {
    private String username;
    private String email;
    private String password;

    /**
     * Преобразует объект RegistrationRequestDto в объект User.
     *
     * @return объект User
     */
    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
