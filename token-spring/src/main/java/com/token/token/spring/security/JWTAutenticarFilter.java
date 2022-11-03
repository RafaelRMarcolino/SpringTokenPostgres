package com.token.token.spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.token.token.spring.data.DetalheUsuarioData;
import com.token.token.spring.model.UsuarioModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {

    // tecla de atalho psfi ,
    // constante de expiração fixa de 10 minutos
    public static final int  TOKEN_EXPIRACAO = 600_000;

    // tecla de atalho psfs ,
    // gerar um GUID https://guidgenerator.com/online-guid-generator.aspx
    // senha para a geração do token
    public static final String TOKEN_SENHA = "82f33f8f-b764-4329-9f25-a55f9507050e";

    private final AuthenticationManager authenticationManager;

    public JWTAutenticarFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // importar ctrl + O attemptAuthentication
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // tratar a autenticação
        try {
            UsuarioModel usuario = new ObjectMapper().readValue(request.getInputStream(), UsuarioModel.class);

            // paramettros para autenticar login password UsernamePasswordAuthenticationToken recebe
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuario.getLogin(),
                    usuario.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuario ", e);
        }
    }


    // CRTL + O + successfulAuthentication
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        DetalheUsuarioData usuarioData = (DetalheUsuarioData) authResult.getPrincipal();
         // definir criar o token colocar as partes do token
        String token = JWT.create().withSubject(usuarioData
                .getUsername())
                // somar o tempo de expiração do token com os milisegundos atuais
                // = data e hora de expiração
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                // assinar o token senha unicA
                .sign(Algorithm.HMAC512(TOKEN_SENHA));

        // Registrar o token no corpo da pagina
        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
