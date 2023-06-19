package pl.lodz.p.it.zzpj.hotelscollector.integration.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.LoginDTO;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Slf4j
@Testcontainers
public class HotelsCollectorIntegrationCoreTest {

    @Setter
    private static String bearerToken = "";

    private static final Network network = Network.newNetwork();

    private static final ObjectMapper mapper = new ObjectMapper();

    @Container
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.1-alpine"))
            .withNetwork(network)
            .withNetworkAliases("db")
            .withDatabaseName("hotel")
            .withUsername("hotel")
            .withPassword("hotel")
            .withExposedPorts(5432);

    @Container
    private static GenericContainer<?> app = new GenericContainer<>(DockerImageName.parse("miszczu1902/zzpj-hotelscollector:latest"))
            .withNetwork(network)
            .withNetworkAliases("app")
            .withExposedPorts(8080)
            .dependsOn(postgres);

    @BeforeAll
    public static void initTests() {
        postgres.start();
        app.start();

        RestAssured.port = app.getMappedPort(8080);
        baseURI = "http://localhost:%s".formatted(RestAssured.port);
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.useRelaxedHTTPSValidation();
        mapper.registerModule(new JavaTimeModule());
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> mapper));
    }

    @AfterAll
    public static void endTests() {
        app.stop();
        postgres.stop();
    }

    protected static Response sendRequestAndGetResponse(Method method, String path, Object object, ContentType contentType) {
        contentType = contentType == null ? ContentType.ANY : contentType;
        RequestSpecification request = given().contentType(contentType);
        String jsonObject = objectToJson(object);

        if (object != null) {
            request.body(jsonObject);
        }
        if (!bearerToken.equals("") && !path.equals("/users/login")) {
            request.header(new Header("Authorization", bearerToken));
        }


        logBeforeRequest(method, path, jsonObject, contentType);
        Response response = request.request(method, baseURI + path);

        logAfterRequest(response);
        return response;
    }

    private static String objectToJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void auth(LoginDTO loginData) {
        bearerToken = sendRequestAndGetResponse(Method.POST, "/users/login", loginData, ContentType.JSON)
                .getHeader("Authorization");
    }

    private static void logMetaData(Method method, String path, ContentType contentType) {
        log.info("Method: %s\nPath: %s\nContent-Type: %s\nAuthorization: %s\n".formatted(method, path, contentType, bearerToken));
    }

    protected static void logBeforeRequest(Method method, String path, String jsonBody, ContentType contentType) {
        logMetaData(method, path, contentType);
        log.info("Request body: %s\n".formatted(jsonBody));
    }

    protected static void logAfterRequest(Response response) {
        log.info("Status code: %s\nResponse body: %s\n".formatted(response.getStatusCode(), response.getBody().asPrettyString()));
    }

}