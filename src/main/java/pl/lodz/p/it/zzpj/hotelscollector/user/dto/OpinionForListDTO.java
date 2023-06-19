package pl.lodz.p.it.zzpj.hotelscollector.user.dto;

import lombok.Data;

@Data
public class OpinionForListDTO {
    private String username;
    private String hotel;
    private String opinion;

    public OpinionForListDTO(String username, String hotel, String opinion) {
        this.username = username;
        this.hotel = hotel;
        this.opinion = opinion;
    }
}
