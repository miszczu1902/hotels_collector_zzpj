package pl.lodz.p.it.zzpj.hotelscollector.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class HotelsCollectorApplicationTests {
    private static final DockerImageName image = DockerImageName.parse("miszczu1902/zzpj-hotelscollector:latest");
    @Container
    private static GenericContainer<?> conatiner = new GenericContainer<>(image)
            .withExposedPorts(8080);
    @BeforeAll
    public static void init() {
        conatiner.start();
    }
    @Test
    public void initTest() {
        assertTrue(true);
    }
    @AfterAll
    public static void end() {
        conatiner.stop();
    }
}