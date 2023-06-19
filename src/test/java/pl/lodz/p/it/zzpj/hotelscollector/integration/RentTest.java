package pl.lodz.p.it.zzpj.hotelscollector.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.lodz.p.it.zzpj.hotelscollector.integration.config.HotelsCollectorIntegrationCoreTest;
import pl.lodz.p.it.zzpj.hotelscollector.integration.util.ObjectFactory;
import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.CreateRentDTO;
import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.RentDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentTest extends HotelsCollectorIntegrationCoreTest {

    @BeforeAll
    public static void initDataToTest() {
        LocalDate now = LocalDate.now();
        CreateRentDTO rent = new CreateRentDTO("TheHubert21", now.plusDays(99).toString(), now.plusDays(100).toString(), -8L);
        sendRequestAndGetResponse(Method.POST, "/rents/reserve", rent, ContentType.JSON);
    }


    @Test
    public void createRentTest() {
        auth(ObjectFactory.clientCredentialsToAuth());
        LocalDate now = LocalDate.now();
        CreateRentDTO rent = new CreateRentDTO("TheHubert21", now.toString(), now.plusDays(1).toString(), -1L);
        Response response = sendRequestAndGetResponse(Method.POST, "/rents/reserve", rent, ContentType.JSON);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void tryToCreateRentCollapsingWithAnotherRentTest() {
        auth(ObjectFactory.clientCredentialsToAuth());
        LocalDate now = LocalDate.now();
        CreateRentDTO rent = new CreateRentDTO("TheHubert21", now.plusDays(29).toString(), now.plusDays(30).toString(), -1L);
        Response response = sendRequestAndGetResponse(Method.POST, "/rents/reserve", rent, ContentType.JSON);
        assertEquals(201, response.getStatusCode());

        response = sendRequestAndGetResponse(Method.POST, "/rents/reserve", rent, ContentType.JSON);
        assertEquals(409, response.getStatusCode());
    }

    @Test
    public void cancelReservationTest() {
        auth(ObjectFactory.managerCredentialsToAuth());
        LocalDate now = LocalDate.now();
        CreateRentDTO rent = new CreateRentDTO("TheHubert21", now.plusDays(10).toString(), now.plusDays(11).toString(), -1L);
        Response response = sendRequestAndGetResponse(Method.POST, "/rents/reserve", rent, ContentType.JSON);
        assertEquals(201, response.getStatusCode());

        String rentId = response.getHeader("Location").replace("/rents/", "");
        response = sendRequestAndGetResponse(Method.PATCH, "/rents/rent/%s/cancel".formatted(rentId), null, null);
        assertEquals(204, response.getStatusCode());
    }

    @Test
    public void tryToCancelNonActiveReservationTest() {
        auth(ObjectFactory.managerCredentialsToAuth());
        LocalDate now = LocalDate.now();
        CreateRentDTO rent = new CreateRentDTO("TheHubert21", now.plusDays(10).toString(), now.plusDays(11).toString(), -1L);
        Response response = sendRequestAndGetResponse(Method.POST, "/rents/reserve", rent, ContentType.JSON);
        assertEquals(201, response.getStatusCode());

        String rentId = response.getHeader("Location").replace("/rents/", "");
        response = sendRequestAndGetResponse(Method.PATCH, "/rents/rent/%s/cancel".formatted(rentId), null, null);
        assertEquals(204, response.getStatusCode());

        response = sendRequestAndGetResponse(Method.PATCH, "/rents/rent/%s/cancel".formatted(rentId), null, null);
        assertEquals(404, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "username"})
    public void getAllRentsTest(String queryParam) {
        auth(ObjectFactory.managerCredentialsToAuth());
        String path = queryParam.equals("") ? "/rents" : "/rents?%s=TheHubert21".formatted(queryParam);
        Response response = sendRequestAndGetResponse(Method.GET, path, null, null);
        assertEquals(200, response.getStatusCode());
        assertFalse(response.body().jsonPath().getList("$", RentDTO.class).isEmpty());
    }

}
