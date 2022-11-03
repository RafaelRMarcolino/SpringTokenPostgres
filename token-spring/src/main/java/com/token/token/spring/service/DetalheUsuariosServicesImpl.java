package com.token.token.spring.service;

import com.token.token.spring.data.DetalheUsuarioData;
import com.token.token.spring.model.UsuarioModel;
import com.token.token.spring.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheUsuariosServicesImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public DetalheUsuariosServicesImpl(UsuarioRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Optional<UsuarioModel> usuario = repository.findByLogin(username);
        if(usuario.isEmpty()){
            throw new UsernameNotFoundException("Usuario [" + username + "] não encontrado");
        }

        return new DetalheUsuarioData(usuario);
    }

}
