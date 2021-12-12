package pwr.pracainz.integrationtests.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * Integration tests use Mockmvc and {@link com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication}
 * to simulate a logged-in user. The Keycloak container is used to test keycloak service.
 *
 * Example integration test:
 * <pre>{@code
 *      public class IntTest extends BaseIntegrationTest {
 *         @Test
 *         @KeycloakPrincipalByUserId("SomeStringValue")
 *         public void test() {
 *             //given
 *
 *             //when
 *             WebTestClient.ResponseSpec spec = webTestClient
 *             		.get()
 *             	    .uri("/uri")
 *             	    .exchange()
 *             (Note! dont use the authorization header here)
 *
 *             //then
 *             ...
 *         }
 *         ...
 *     }
 * }</pre>
 *
 * Example integration test when the user needs to be set inside the test:
 * <pre>{@code
 *      public class IntTest extends BaseIntegrationTest {
 *         @Test
 *         @WithMockAuthentication(authType = KeycloakAuthenticationToken.class)
 *         public void test() {
 *             //given
 *             changeLoggedInUserTo("userId");
 *
 *             //when
 *             WebTestClient.ResponseSpec spec = webTestClient
 *             		.get()
 *             	    .uri("/uri")
 *             	    .exchange()
 *             (Note! dont use the authorization header here)
 *
 *             //then
 *             ...
 *         }
 *         ...
 *     }
 * }</pre>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Testcontainers
@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
@Sql(scripts = {"classpath:schema.sql", "classpath:data-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseIntegrationTest {

	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	protected TestI18nService i18nService;

	protected WebTestClient webTestClient;

	/**
	 * Container for the MySQL database. Will be reused for each test without restart.
	 */
	protected static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.26")
			.withDatabaseName("data")
				.withUsername("backendUser")
				.withPassword("backendPassword2")
				.waitingFor(new HttpWaitStrategy().forPort(3606))
			.withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(BaseIntegrationTest.class)))
			.withReuse(true);

	/**
	 * Container for keycloak. For tests, it does not connect to the mysql database. Will be reused for each test without restart.
	 */
	protected static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:15.0.0")
			.withExposedPorts(8080)
			.withRealmImportFile("./keycloak/realm/realm-export.json")
			.withAdminUsername("admin")
			.withAdminPassword("Password1")
			.withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(BaseIntegrationTest.class)))
			.withReuse(true);

	static {
		mysql.start();
		keycloak.start();
	}

	@BeforeEach
	public void setup() {
		MockMvc mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();

		this.webTestClient = MockMvcWebTestClient
				.bindTo(mockMvc)
				.build();

		assertThat(keycloak.isRunning(), is(Boolean.TRUE));
		assertThat(mysql.isRunning(), is(Boolean.TRUE));
	}

	/**
	 * Add container information for properties
	 * @param registry The registry holding the properties
	 */
	@DynamicPropertySource
	static void registerContainerProperties(DynamicPropertyRegistry registry) {
		//The anilist api should be set here to the wireMock address
		registry.add("anilist.apiUrl", () -> "http://localhost:9090");
		registry.add("keycloak.auth-server-url", keycloak::getAuthServerUrl);
		registry.add("keycloakrealm.master.url", keycloak::getAuthServerUrl);
		registry.add("keycloakrealm.clientserver.url", () -> String.format("%s/realms/PracaInz/protocol/openid-connect", keycloak.getAuthServerUrl()));
		registry.add("spring.datasource.url", mysql::getJdbcUrl);
	}

	protected static <T> Matcher<T> hasGraph(String graphPath, Matcher<T> matcher) {
		List<String> properties = Arrays.asList(graphPath.split("\\."));
		ListIterator<String> iterator =
				properties.listIterator(properties.size());

		Matcher<T> ret = matcher;
		while (iterator.hasPrevious()) {
			ret = hasProperty(iterator.previous(), ret);
		}
		return ret;
	}

	protected void changeLoggedInUserTo(String userId) {
		final var auth = (KeycloakAuthenticationToken) TestSecurityContextHolder.getContext().getAuthentication();
		when(auth.getPrincipal()).thenReturn(userId);
	}
}
