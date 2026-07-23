package br.com.todaylist.filter;

import br.com.todaylist.user.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Base64;

@Component // Here, every request passes through this filter.
public class filterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private UserRepository userRepository;

  @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

            var authorization = request.getHeader("Authorization");
      System.out.println("Authorization: ");
      System.out.println(authorization);

      var authEncoded = authorization.substring("Basic ".length()).trim(); // .trim() <- remove all spaces

      byte[] authDecode = Base64.getDecoder().decode(authEncoded);
      var authString = new String(authDecode);

      String [] credentials = authString.split(":");
      String username = credentials[0];
      String password = credentials[1];
      System.out.println("Authorization");
      System.out.println(username);
      System.out.println(password);

          //validation users
      var users = this.userRepository.findByUsername(username);
      if(users == null){
        response.sendError(401, "Usuário sem Autorização");
      }else {
        var passwordVerify = BCrypt.verifier().verify(password.toCharArray(), users.getPassword());
        if (passwordVerify.verified)
          filterChain.doFilter(request, response);
        else {
          response.sendError(401);
        }
      }
    }
}
