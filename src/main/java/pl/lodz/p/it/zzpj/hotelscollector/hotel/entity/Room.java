package pl.lodz.p.it.zzpj.hotelscollector.hotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "room")
public class Room {
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

    @ElementCollection
    @CollectionTable(name = "room_facilities", joinColumns = @JoinColumn(name = "room_id"))
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
    private Hotel hotel;
}