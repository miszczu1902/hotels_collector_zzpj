package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.controller.HotelMapper;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class HotelService {

    private final String API_KEY = "dd1650c3121b4795b6f161644231706";
    private final HotelRepository hotelRepository;

    @Transactional
    public long addHotel(HotelRequest hotelRequest) {
        HotelEntity createdHotel = hotelRepository.save(HotelMapper.toHotelEntity(hotelRequest));
        return createdHotel.getId();
    }

    @Transactional
    public Optional<HotelEntity> getHotelById(String id) {
        return hotelRepository.findById(id);
    }

    @Transactional
    public List<HotelEntity> getAllHotels() {
        var hotels = hotelRepository.findAll();
        if (hotels.isEmpty()) {
            return null;
        }
        return hotels;
    }


    @Transactional
    public boolean deleteHotel(String id) {
        var hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            hotelRepository.delete(hotel.get());
        } else {
            return false;
        }
        return true;
    }

    public String getWeather(String id, String forecastDays) {
        var hotel = hotelRepository.findById(id);
        if (hotel.isEmpty()) {
            return null;
        }

        String latitude = hotel.get().getLatitude();
        String longitude = hotel.get().getLongitude();

        String forecastUrl = "https://api.weatherapi.com/v1/forecast.json?key=" + API_KEY +
                "&q=" + latitude + "," + longitude + "&days=" + forecastDays;

        try {
            // Tworzenie URL z adresem API pogodowym
            URL url = new URL(forecastUrl);

            // Nawiązywanie połączenia HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Pobieranie odpowiedzi z API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Przetwarzanie odpowiedzi z API
            String jsonResponse = response.toString();

            // Zamykanie połączenia
            connection.disconnect();

            return jsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
