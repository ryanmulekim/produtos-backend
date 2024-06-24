package estagio.projeto.service.impl;


import estagio.projeto.domain.user.User;
import estagio.projeto.domain.user.UserDto;
import estagio.projeto.domain.user.UserRequestDto;
import estagio.projeto.repository.UserRepository;
import estagio.projeto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return mapperReturnListUserDto(users);
    }

    @Override
    public UserDto createNewUser(UserRequestDto userRequestDto) {
        verifyLoginExists(userRequestDto);
        User user = saveNewUser(userRequestDto);

        return mapperReturnUserDto(user);
    }

    @Override
    public UserDto getByIdUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado!!"));

        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }

    @Override
    @Transactional
    public void deleteByIdUsers(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado!!"));
        userRepository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public User updateByIdUsers(String id, UserRequestDto data) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));


        if (data.getLogin() != null) {
            existingUser.setLogin(data.getLogin());
        }

        if (data.getPassword() != null) {
            existingUser.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));
        }

        if (data.getRole() != null) {
            existingUser.setRole(data.getRole());
        }

        return userRepository.save(existingUser);
    }

    private User saveNewUser(UserRequestDto userRequestDto){
        User build = User.builder()

                .role(userRequestDto.getRole())
                .login(Objects.requireNonNullElse(userRequestDto.getLogin(), ""))
                .password(new BCryptPasswordEncoder().encode(userRequestDto.getPassword()))
                .build();

        return userRepository.save(build);

    }


    private void verifyLoginExists(UserRequestDto userRequestDto) {
        UserDetails login = userRepository.findByLogin(userRequestDto.getLogin());
        if(!Objects.isNull(login)){
            throw new RuntimeException("Login ja existente!!");
        }
    }

    private List<UserDto> mapperReturnListUserDto(List<User> users){
        return users.stream().map(user -> {
            return UserDto.builder()
                    .id(user.getId())
                    .login(user.getLogin())
                    .build();
        }).toList();

    }
    private UserDto mapperReturnUserDto(User user){
            return UserDto.builder()
                    .id(user.getId())
                    .login(user.getLogin())
                    .password(user.getPassword())
                    .build();
    }
}
