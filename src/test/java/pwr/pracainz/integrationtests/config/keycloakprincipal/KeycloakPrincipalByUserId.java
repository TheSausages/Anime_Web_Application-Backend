package pwr.pracainz.integrationtests.config.keycloakprincipal;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@WithMockAuthentication(authType = KeycloakAuthenticationToken.class)
@ExtendWith(ChangePrincipalToUserId.class)
public @interface KeycloakPrincipalByUserId {
	String value() default "";
}
