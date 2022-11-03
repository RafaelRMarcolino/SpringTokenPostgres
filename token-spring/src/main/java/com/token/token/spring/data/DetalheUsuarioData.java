package com.token.token.spring.data;

import com.token.token.spring.model.UsuarioModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

//userDatail do pacote security

public class DetalheUsuarioData  implements UserDetails {

    //para receber o usuario
    private final Optional<UsuarioModel> usuario;

    public DetalheUsuarioData(Optional<UsuarioModel> usuario) {
        this.usuario = usuario;
    }

    //permições do usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // elterar pois no sera usado por enquanto
        return new ArrayList<>();
    }

    // adicionar a excption para casa orra algum erro
    @Override
    public String getPassword() {
        return usuario.orElse(new UsuarioModel()).getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new UsuarioModel()).getLogin();
    }

    // retornar true para que valide o acesso ao usuario

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
