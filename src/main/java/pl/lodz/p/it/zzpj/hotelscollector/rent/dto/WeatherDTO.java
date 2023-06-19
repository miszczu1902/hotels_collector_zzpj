package pl.lodz.p.it.zzpj.hotelscollector.rent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {
    private LocationDTO location;
    private ForecastDTO forecast;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocationDTO {
        private String name;
        private String region;
        private String country;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ForecastDTO {
        private List<ForecastdayDTO> forecastday;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ForecastdayDTO {
        private LocalDate date;
        private DayDTO day;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DayDTO {
        private Double mintemp_c;
        private Double maxtemp_c;
        private Double avgtemp_c;
        private Double maxwind_kph;
        private Double avghumidity;
    }
}

