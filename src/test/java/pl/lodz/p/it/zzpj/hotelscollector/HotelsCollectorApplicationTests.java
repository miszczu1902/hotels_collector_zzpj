package pl.lodz.p.it.zzpj.hotelscollector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.parsing.Parser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

@Testcontainers
class HotelsCollectorApplicationTests {

    private static final DockerImageName APP_IMAGE = DockerImageName.parse("zzpj-hotelscollector:latest");
    private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:latest");
    private static Logger logger = LoggerFactory.getLogger("testcontainers-config");
    private static final ObjectMapper mapper = new ObjectMapper();

    @Container
    private static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withLogConsumer(new Slf4jLogConsumer(logger))
            .withDatabaseName("hotel")
            .withUsername("hotel")
            .withPassword("hotel")
            .withExposedPorts(5432);

    @Container
    private static GenericContainer<?> APP = new GenericContainer<>(APP_IMAGE)
            .withLogConsumer(new Slf4jLogConsumer(logger))
            .withExposedPorts(8080)
            .withExposedPorts(5432)
            .dependsOn(POSTGRES);

    protected static int POSTGRES_PORT;
    protected static int APP_PORT;

    @BeforeClass
    public static void initTestContainers() {
        try (Network network = Network.newNetwork()) {
            POSTGRES.withNetwork(network).withNetworkAliases("db");
            APP.withNetwork(network).withNetworkAliases("hotelscollector");
            POSTGRES.start();
            POSTGRES_PORT = APP.getMappedPort(5432);

            APP.start();
            APP_PORT = APP.getMappedPort(8080);

            baseURI = String.format("http://localhost/%s/api", APP_PORT);
            port = APP_PORT;
            defaultParser = Parser.JSON;
            RestAssured.useRelaxedHTTPSValidation();
            mapper.registerModule(new JavaTimeModule());
            RestAssured.config = RestAssured.config()
                    .objectMapperConfig(new ObjectMapperConfig()
                            .jackson2ObjectMapperFactory(
                                    (cls, charset) -> mapper
                            ));

            logger.info(String.format("URI: %s", baseURI));
            logger.info(String.format("Default port: %s", port));
            logger.info(String.format("Default parser: %s", defaultParser));
            logger.info(String.format("Postgres port:%s", POSTGRES_PORT));
            logger.info(String.format("App port:%s", APP));
        } catch (Throwable e) {
            throw new RuntimeException("Container run failed!");
        }
    }

    @Test
    void checkContainerStatusTest() {
        assertTrue(true);
    }

    @AfterClass
    public static void endTest() {
        APP.stop();
        POSTGRES.stop();
        logger.info("Container stopped");
    }

}
