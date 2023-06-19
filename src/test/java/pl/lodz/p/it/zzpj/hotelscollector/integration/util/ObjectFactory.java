package pl.lodz.p.it.zzpj.hotelscollector.integration.util;

import org.openapitools.model.*;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.AddOpinionDTO;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.BlockUserDTO;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.LoginDTO;

import java.math.BigDecimal;
import java.util.List;

public class ObjectFactory {

    public static LoginDTO clientCredentialsToAuth() {
        return new LoginDTO("TheHubert21", "TheHubert21.");
    }

    public static LoginDTO adminCredentialsToAuth() {
        return new LoginDTO("BartoszMiszczak", "TheHubert21.");
    }

    public static LoginDTO managerCredentialsToAuth() {
        return new LoginDTO("HubertCzacha4", "TheHubert21.");
    }

    public static BlockUserDTO createBlockUserDTO(String username) {
        return new BlockUserDTO(username);
    }

    public static AddOpinionDTO createOpinion() {
        return new AddOpinionDTO("Fajny hotel. MASNO FEST!");
    }

    public static HotelRequest hotelToAdd() {
        HotelRequest hotelRequest = new HotelRequest();
        hotelRequest.setName(RandomStringUtils.randomAlphabetic(10));
        hotelRequest.setLongitude("19°29′34″E");
        hotelRequest.setLatitude("49°53′00″N");
        hotelRequest.setCity(RandomStringUtils.randomAlphabetic(10));
        hotelRequest.setStreet(RandomStringUtils.randomAlphabetic(10));
        hotelRequest.setNumber(RandomStringUtils.randomNumeric(10));
        hotelRequest.setPhoneNumber(RandomStringUtils.randomNumeric(9));
        hotelRequest.setAdditionalAddressInformation(RandomStringUtils.randomAlphabetic(20));
        return hotelRequest;
    }

    public static RoomRequest roomToAdd() {
        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setRoomSize(new BigDecimal(RandomUtils.nextInt(10, 50)));
        roomRequest.setMaximumGuestNumber(RandomUtils.nextInt(1, 10));
        roomRequest.setIsAirConditioning(RandomUtils.nextBoolean());
        roomRequest.setIsSoundproofing(RandomUtils.nextBoolean());
        roomRequest.setDescription(RandomStringUtils.randomAlphabetic(100));
        roomRequest.setRoomFacilities(List.of(RoomFacilitiesResponse.ELECTRIC_KETTLE, RoomFacilitiesResponse.FLAT_SCREEN_TV));
        return roomRequest;
    }

    public static EditRoomRequest roomToEdit() {
        EditRoomRequest roomRequest = new EditRoomRequest();
        roomRequest.setMaximumGuestNumber(RandomUtils.nextInt(1, 10));
        roomRequest.setIsAirConditioning(RandomUtils.nextBoolean());
        roomRequest.setIsSoundproofing(RandomUtils.nextBoolean());
        roomRequest.setDescription(RandomStringUtils.randomAlphabetic(100));
        roomRequest.setRoomFacilities(List.of(RoomFacilitiesResponse.ELECTRIC_KETTLE, RoomFacilitiesResponse.FLAT_SCREEN_TV));
        return roomRequest;
    }
}
