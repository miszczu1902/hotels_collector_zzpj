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
    @Column(name = "room_id")
    private long id;

    @DecimalMin(value = "0")
    @Column(name = "room_size", nullable = false)
    private long roomSize;

    @DecimalMin(value = "1")
    @Column(name = "maximum_guest_number", nullable = false)
    private int maximumGuestNumber;

    @NotNull
    @Column(name = "facility")
    @Enumerated(EnumType.STRING)
    private List<Facilite> roomFacilities  = new ArrayList<>();

    @Column(name = "is_air_conditioning", nullable = false)
    private boolean isAirConditioning;

    @Column(name = "is_soundproofing", nullable = false)
    private boolean isSoundproofing;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotelEntity;

    public RoomEntity(long roomSize, int maximumGuestNumber, List<Facilite> roomFacilities, boolean isAirConditioning, boolean isSoundproofing, String description, HotelEntity hotelEntity) {
        this.roomSize = roomSize;
        this.maximumGuestNumber = maximumGuestNumber;
        this.roomFacilities = roomFacilities;
        this.isAirConditioning = isAirConditioning;
        this.isSoundproofing = isSoundproofing;
        this.description = description;
        this.hotelEntity = hotelEntity;
    }
}