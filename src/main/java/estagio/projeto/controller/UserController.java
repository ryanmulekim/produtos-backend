package estagio.projeto.controller;

import estagio.projeto.domain.user.UserDto;
import estagio.projeto.domain.user.UserRequestDto;
import estagio.projeto.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("")
    public ResponseEntity getAllUsers(){

        try{
            List<UserDto> allUser = userService.getAllUser();
            return ResponseEntity.ok(allUser);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error no servidor, por favor tente mais tarde.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getByIdUsers(@PathVariable("id") String id){

        try{

            return ResponseEntity.ok( userService.getByIdUser(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity updateByIdUsers(@PathVariable("id") String id,@RequestBody @Valid UserRequestDto data){

        try{

            return ResponseEntity.ok( userService.updateByIdUsers(id,data));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteByIdUsers(@PathVariable("id") String id){

        try{
            userService.deleteByIdUsers(id);
            return ResponseEntity.ok("Usuario de " + id +" deletado com sucesso");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
