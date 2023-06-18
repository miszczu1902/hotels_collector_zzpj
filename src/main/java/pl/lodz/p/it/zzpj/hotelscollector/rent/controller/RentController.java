package pl.lodz.p.it.zzpj.hotelscollector.rent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.HotelService;
import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.CreateRentDTO;
import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.RentDTO;
import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.WeatherDTO;
import pl.lodz.p.it.zzpj.hotelscollector.rent.entity.RentEntity;
import pl.lodz.p.it.zzpj.hotelscollector.rent.services.RentService;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.JsonParserException;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.RentNotExistException;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.RoomNotAvailableException;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static pl.lodz.p.it.zzpj.hotelscollector.utils.Constans.URI_RENTS;

@RequiredArgsConstructor
@RestController
@RequestMapping(URI_RENTS)
public class RentController {

    private final RentService rentService;

    private final HotelService hotelService;

    private final ObjectMapper objectMapper;

    @PostMapping("/reserve")
    public ResponseEntity<RentDTO> reserveRoom(@RequestBody CreateRentDTO createRentDTO) throws JsonParserException, RoomNotAvailableException {
        RentEntity rentEntity = rentService.reserveRoom(createRentDTO).orElseThrow();
        WeatherDTO weather;
        try {
            weather = objectMapper.readValue(
                    hotelService.getWeather(
                            String.valueOf(rentEntity.getRoom().getHotelEntity().getId()),
                            String.valueOf(ChronoUnit.DAYS.between(rentEntity.getBeginTime(), rentEntity.getEndTime()))),
                    WeatherDTO.class);
        } catch (JsonProcessingException e) {
            throw new JsonParserException(e.getMessage(), e);
        }

        weather.getForecast().setForecastday(weather.getForecast().getForecastday().stream()
                .filter(day -> {
                    LocalDate date = day.getDate();
                    return (date.isEqual(rentEntity.getBeginTime()) || date.isEqual(rentEntity.getEndTime()))
                            || (date.isAfter(rentEntity.getBeginTime()) && date.isBefore(rentEntity.getEndTime()));
                }).toList());

        return ResponseEntity.created(URI.create("%s/%s".formatted(URI_RENTS, rentEntity.getId())))
                .body(RentMapper.createRentDTOWithWeather(rentEntity, weather));
    }

    @GetMapping("/rent/{rentId}")
    public ResponseEntity<RentDTO> getRentByRentId(@PathVariable String rentId) {
        return rentService.getRentById(rentId).map(rent ->
                        ResponseEntity.ok().body(RentMapper.createRentDTO(rent)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<RentDTO>> getAllRents(@RequestParam(required = false) Optional<String> username) {
        return ResponseEntity.ok()
                .body(username.map(param -> rentService.getAllRentsByUsername(param).stream()
                                .map(RentMapper::createRentDTO).toList())
                        .orElseGet(() -> rentService.getAllRents().stream()
                                .map(RentMapper::createRentDTO).toList()));
    }


    @PatchMapping("/rent/{rentId}/cancel")
    public ResponseEntity<Object> cancelRent(@PathVariable String rentId) throws RentNotExistException {
        rentService.endReservation(rentId);
        return ResponseEntity.noContent().build();
    }
}
