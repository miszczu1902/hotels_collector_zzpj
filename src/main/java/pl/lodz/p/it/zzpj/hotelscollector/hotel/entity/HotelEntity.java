package pl.lodz.p.it.zzpj.hotelscollector.hotel.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.OpinionEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "additional_address_information", nullable = false)
    private String additionalAddressInformation;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Setter
    @OneToMany(mappedBy = "hotelEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomEntity> roomEntities = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "hotelEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpinionEntity> opinionEntities = new ArrayList<>();

    public HotelEntity(String name, String longitude, String latitude, String city, String street, String number, String additionalAddressInformation, String phoneNumber, List<RoomEntity> roomEntities) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.street = street;
        this.number = number;
        this.additionalAddressInformation = additionalAddressInformation;
        this.phoneNumber = phoneNumber;
        this.roomEntities = roomEntities;
    }
}
