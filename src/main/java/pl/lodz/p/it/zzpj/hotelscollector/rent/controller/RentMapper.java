package pl.lodz.p.it.zzpj.hotelscollector.rent.controller;

import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.RentDTO;
import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.WeatherDTO;
import pl.lodz.p.it.zzpj.hotelscollector.rent.entity.RentEntity;

public class RentMapper {

    public static RentDTO createRentDTO(RentEntity rent) {
        return new RentDTO(rent.getId().toString(), rent.getUser().getUsername(),
                rent.getBeginTime(), rent.getEndTime(), rent.getRoom().getId(), rent.getRentCost());
    }

    public static RentDTO createRentDTOWithWeather(RentEntity rent, WeatherDTO weather) {
        return new RentDTO(rent.getId().toString(), rent.getUser().getUsername(),
                rent.getBeginTime(), rent.getEndTime(), rent.getRoom().getId(), rent.getRentCost(), weather);
    }
}
