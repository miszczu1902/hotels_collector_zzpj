package pl.lodz.p.it.zzpj.hotelscollector.hotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class RoomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @DecimalMin(value = "0")
    @Column(name = "room_size", nullable = false)
    private long roomSize;

    @DecimalMin(value = "1")
    @Setter
    @Column(name = "maximum_guest_number", nullable = false)
    private int maximumGuestNumber;

    @NotNull
    @Setter
    @Column(name = "facility")
    private String roomFacilities;

    @Setter
    @Column(name = "is_air_conditioning", nullable = false)
    private boolean isAirConditioning;

    @Setter
    @Column(name = "is_soundproofing", nullable = false)
    private boolean isSoundproofing;

    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @Setter
    private HotelEntity hotelEntity;

    public RoomEntity(long roomSize, int maximumGuestNumber, String roomFacilities, boolean isAirConditioning, boolean isSoundproofing, String description, HotelEntity hotelEntity) {
        this.roomSize = roomSize;
        this.maximumGuestNumber = maximumGuestNumber;
        this.roomFacilities = roomFacilities;
        this.isAirConditioning = isAirConditioning;
        this.isSoundproofing = isSoundproofing;
        this.description = description;
        this.hotelEntity = hotelEntity;
    }
}