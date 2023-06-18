package pl.lodz.p.it.zzpj.hotelscollector.rent.dto;

public record CreateRentDTO(String username,
                            String beginTime,
                            String endTime,
                            Long roomId) {
}
