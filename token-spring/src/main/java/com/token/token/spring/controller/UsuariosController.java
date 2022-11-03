package com.token.token.spring.controller;

import com.token.token.spring.model.UsuarioModel;
import com.token.token.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    private final UsuarioRepository repository;

    public UsuariosController(UsuarioRepository repository){
        this.repository = repository;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(){
        return ResponseEntity.ok(repository.findAll());

    }

    @PostMapping("/listarTodos")
    public ResponseEntity<UsuarioModel> insertUsuarios(@RequestBody UsuarioModel usuarioModel){
        return ResponseEntity.ok(repository.save(usuarioModel));

    }

}
