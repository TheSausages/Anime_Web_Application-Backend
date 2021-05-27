package pwr.PracaInz.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
public class RestSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    JWTConfiguration configuration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        String token = Jwts.builder()
                .setSubject(principal.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + configuration.getExpirationTime()))
                .signWith(getSigningKey()).compact();


        response.addHeader("Authorization", "Bearer " + token);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(configuration.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
