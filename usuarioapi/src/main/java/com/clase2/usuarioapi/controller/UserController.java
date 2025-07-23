package com.clase2.usuarioapi.controller;

import com.clase2.usuarioapi.model.User;
import com.clase2.usuarioapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<>("Usuario Guardado", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, String>>> listUser(){
        List<Map<String, String>> list = userService.listSummary();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email){
        User user = userService.getByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user){
        boolean update = userService.updateUser(user.getEmail(),user);
        String msj = update ? "Usuario Actualizado" : "Usuario No Encontrado";
        return new ResponseEntity<>(msj,HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email){
        boolean delete = userService.deleteUser(email);
        String msj = delete ? "Usuario Eliminado" : "Usuario No Eliminado";
        return new ResponseEntity<>(msj,HttpStatus.OK);
    }
}
