package com.token.token.spring.controller;

import com.token.token.spring.model.UsuarioModel;
import com.token.token.spring.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    private final UsuarioRepository repository;

    // adicionando o decoder
    private final PasswordEncoder encoder;

    public UsuariosController(UsuarioRepository repository, PasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(){
        return ResponseEntity.ok(repository.findAll());

    }

    @PostMapping("/listarTodos")
    public ResponseEntity<UsuarioModel> insertUsuarios(@RequestBody UsuarioModel usuarioModel){
       usuarioModel.setPassword(encoder.encode(usuarioModel.getPassword()));
        return ResponseEntity.ok(repository.save(usuarioModel));

    }


    //Rest para validar a senha
    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String password){
        // consulta pelo login
        Optional<UsuarioModel> optUsuario = repository.findByLogin(login);
        if(optUsuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }


        UsuarioModel usuario = optUsuario.get();
        // verificar se a senha que esta cadastrada e igual a senha digitada
       boolean valid = encoder.matches(password, optUsuario.get().getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);

        // testar na url http://localhost:8080/api/usuarios/validarSenha

    }

}
