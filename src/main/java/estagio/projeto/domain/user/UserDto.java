package estagio.projeto.domain.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class UserDto {

    private String id;
    private String login;
    private String password;


}
