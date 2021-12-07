package pwr.pracainz.integrationtests.config.keycloakprincipal;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstantiationException;
import org.junit.platform.commons.util.StringUtils;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;

import java.util.Objects;

import static org.mockito.Mockito.when;

class ChangePrincipalToUserId implements BeforeTestExecutionCallback {
	@Override
	public void beforeTestExecution(ExtensionContext context) {
		if (Objects.isNull(context) || context.getElement().isEmpty() || StringUtils.isBlank(context.getElement().get().getAnnotation(KeycloakPrincipalByUserId.class).value())) {
			throw new TestInstantiationException("Could not set Principal");
		}

		final var auth = (KeycloakAuthenticationToken) TestSecurityContextHolder.getContext().getAuthentication();
		when(auth.getPrincipal()).thenReturn(context.getElement().get().getAnnotation(KeycloakPrincipalByUserId.class).value());
	}
}