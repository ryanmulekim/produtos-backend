package estagio.projeto.service;

import estagio.projeto.domain.user.User;
import estagio.projeto.domain.user.UserDto;
import estagio.projeto.domain.user.UserRequestDto;

import java.util.List;


public interface UserService {

    List<UserDto> getAllUser();

    UserDto createNewUser(UserRequestDto userRequestDto);

    UserDto getByIdUser(String id);

    void deleteByIdUsers(String id);

    User updateByIdUsers(String id, UserRequestDto data);
}
