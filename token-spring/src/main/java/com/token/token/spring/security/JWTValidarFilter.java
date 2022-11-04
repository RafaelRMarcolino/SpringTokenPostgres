package com.token.token.spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTValidarFilter extends BasicAuthenticationFilter {

    // mandar para o cabeçalho
    public static final String HEDER_ATRIBUTO = "Authorization";

    public static final String ATRIBUTO_PREFIXO = "Bearer";

    public JWTValidarFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // sobreescrever o doFilterInternal
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String atributo = request.getHeader(HEDER_ATRIBUTO);

        if(atributo == null){

            chain.doFilter(request, response);
        }

        if(!atributo.startsWith(ATRIBUTO_PREFIXO)){
            chain.doFilter(request, response);
        }

        // pedaço do token seu prefixo replace substitui o token prefixo por um vazio
        String token = atributo.replace(ATRIBUTO_PREFIXO, "");

        // retornandoi o usuario e o password token
       UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response) ;
    }



    // interpretação do token
    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){

        // retirar o nome do usuario
        String usuario = JWT.require(Algorithm.HMAC512(JWTAutenticarFilter.TOKEN_SENHA))
                // criar token
                .build()
                // verificar o token
                .verify(token)
                //se fez a validação retorna o nome do usuario
                .getSubject();

        // se usuario for null
        if(usuario == null){
            return null;
        }

        // metodo get autentication
        return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());

    }



}
