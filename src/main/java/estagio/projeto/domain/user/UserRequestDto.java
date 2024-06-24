package estagio.projeto.domain.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestDto {

    private String login;
    private String password;
    private UserRole role;
}