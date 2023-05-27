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
    @Column(name = "id")
    private long id;

    @DecimalMin(value = "0")
    @Column(name = "room_size", nullable = false)
    private long roomSize;

    @DecimalMin(value = "1")
    @Column(name = "maximum_guest_number", nullable = false)
    private int maximumGuestNumber;

    @OneToMany(mappedBy = "room")
    private List<Facilite> roomFacilities  = new ArrayList<>();

    @Column(name = "is_air_conditioning", nullable = false)
    private boolean isAirConditioning;

    @Column(name = "is_soundproofing", nullable = false)
    private boolean isSoundproofing;

    @Column(name = "description", nullable = false)
    private String description;
}