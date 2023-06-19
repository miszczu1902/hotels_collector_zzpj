package pl.lodz.p.it.zzpj.hotelscollector.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.zzpj.hotelscollector.integration.config.HotelsCollectorIntegrationCoreTest;
import pl.lodz.p.it.zzpj.hotelscollector.integration.util.ObjectFactory;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends HotelsCollectorIntegrationCoreTest {

    @BeforeEach
    public void initTest() {
        setBearerToken("");
    }

    @Test
    public void loginTest() {
        Response response = sendRequestAndGetResponse(Method.POST, "/users/login", ObjectFactory.clientCredentialsToAuth(), ContentType.JSON);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getHeader("Authorization"));
    }

    @Test
    public void blockUserAndUnlockHimThenTest() {
        auth(ObjectFactory.adminCredentialsToAuth());

        Response response = sendRequestAndGetResponse(Method.PATCH, "/users/block-user", ObjectFactory.createBlockUserDTO("BartekGit1"), ContentType.JSON);
        assertEquals(200, response.getStatusCode());

        sendRequestAndGetResponse(Method.PATCH, "/users/block-user", ObjectFactory.createBlockUserDTO("BartekGit1"), ContentType.JSON);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void blockUserAndUnlockHimThenAsClientTest() {
        auth(ObjectFactory.clientCredentialsToAuth());

        Response response = sendRequestAndGetResponse(Method.PATCH, "/users/block-user", ObjectFactory.createBlockUserDTO("BartekGit1"), ContentType.JSON);
        assertEquals(403, response.getStatusCode());

        sendRequestAndGetResponse(Method.PATCH, "/users/block-user", ObjectFactory.createBlockUserDTO("BartekGit1"), ContentType.JSON);
        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void addOpinionTest() {
        auth(ObjectFactory.clientCredentialsToAuth());

        Response response = sendRequestAndGetResponse(Method.POST, "/hotels/hotel", ObjectFactory.hotelToAdd(), ContentType.JSON);
        assertEquals(201, response.getStatusCode());

        String hotelId = response.getHeader("Location").replace("/hotels/hotel/", "");
        response = sendRequestAndGetResponse(Method.POST, "/users/add-opinion/%s".formatted(hotelId), ObjectFactory.createOpinion(), ContentType.JSON);
        assertEquals(200, response.getStatusCode());
    }
}
