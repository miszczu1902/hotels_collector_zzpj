package pl.lodz.p.it.zzpj.hotelscollector.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.HotelResponse;
import org.openapitools.model.RoomResponse;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import pl.lodz.p.it.zzpj.hotelscollector.integration.config.HotelsCollectorIntegrationCoreTest;
import pl.lodz.p.it.zzpj.hotelscollector.integration.util.ObjectFactory;

import static org.junit.jupiter.api.Assertions.*;

public class HotelTest extends HotelsCollectorIntegrationCoreTest {

    private int getRandomHotelId() {
        return RandomUtils.nextInt(1, 11) * (-1);
    }

    private int getRandomRoomId() {
        return RandomUtils.nextInt(1, 21) * (-1);
    }

    @BeforeEach
    public void init() {
        auth(ObjectFactory.clientCredentialsToAuth());
    }

    @Test
    public void addHotelTest() {
        Response response = sendRequestAndGetResponse(Method.POST, "/hotels/hotel", ObjectFactory.hotelToAdd(), ContentType.JSON);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void addRoomToHotelTest() {
        Response response = sendRequestAndGetResponse(Method.POST, "/hotels/hotel", ObjectFactory.hotelToAdd(), ContentType.JSON);
        assertEquals(201, response.getStatusCode());

        String hotelId = response.getHeader("Location").replace("/hotels/hotel/", "");
        response = sendRequestAndGetResponse(Method.POST, "/hotels/hotel/%s/room".formatted(hotelId), ObjectFactory.roomToAdd(), ContentType.JSON);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void getHotelByIdTest() {
        Response response = sendRequestAndGetResponse(Method.GET, "/hotels/hotel/%s".formatted(getRandomHotelId()), null, null);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody().jsonPath().getObject("$", HotelResponse.class));
    }

    @Test
    public void getRoomByIdTest() {
        Response response = sendRequestAndGetResponse(Method.GET, "/hotels/hotel/room/%s".formatted(getRandomRoomId()), null, null);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody().jsonPath().getObject("$", RoomResponse.class));
    }

    @Test
    public void editRoomTest() {
        Response response = sendRequestAndGetResponse(Method.PUT, "/hotels/hotel/room/%s".formatted(getRandomRoomId()), ObjectFactory.roomToEdit(), ContentType.JSON);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void editNotExistingRoomTest() {
        Response response = sendRequestAndGetResponse(Method.PUT, "/hotels/hotel/room/%s".formatted(RandomUtils.nextInt(100, 200)), ObjectFactory.roomToEdit(), ContentType.JSON);
        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void deleteRoomAndHotelNextTest() {
        Response response = sendRequestAndGetResponse(Method.POST, "/hotels/hotel", ObjectFactory.hotelToAdd(), ContentType.JSON);
        assertEquals(201, response.getStatusCode());

        String hotelId = response.getHeader("Location").replace("/hotels/hotel/", "");
        response = sendRequestAndGetResponse(Method.POST, "/hotels/hotel/%s/room".formatted(hotelId), ObjectFactory.roomToAdd(), ContentType.JSON);
        assertEquals(201, response.getStatusCode());

        String roomId = response.getHeader("Location").replace("/hotels/room/", "");
        response = sendRequestAndGetResponse(Method.DELETE, "/hotels/hotel/room/%s".formatted(roomId), null, null);
        assertEquals(200, response.getStatusCode());

        response = sendRequestAndGetResponse(Method.DELETE, "/hotels/hotel/%s".formatted(hotelId), null, null);
        assertEquals(200, response.getStatusCode());
    }
}
