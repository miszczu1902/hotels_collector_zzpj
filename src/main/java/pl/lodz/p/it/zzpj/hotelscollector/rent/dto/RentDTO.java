package pl.lodz.p.it.zzpj.hotelscollector.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDTO {
    private String id;
    private String username;
    private LocalDate beginTime;
    private LocalDate endTime;
    private Long roomId;
    private Double cost;
    private WeatherDTO weather;

    public RentDTO(String id, String username, LocalDate beginTime, LocalDate endTime, Long roomId, Double cost) {
        this.id = id;
        this.username = username;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.roomId = roomId;
        this.cost = cost;
    }
}
