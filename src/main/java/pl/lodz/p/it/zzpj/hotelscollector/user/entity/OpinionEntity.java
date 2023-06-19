package pl.lodz.p.it.zzpj.hotelscollector.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "opinions")
public class OpinionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Setter
    @Column(name = "opinion", nullable = false)
    private String opinion;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @Setter
    private HotelEntity hotelEntity;

    public OpinionEntity(String opinion, UserEntity userEntity, HotelEntity hotelEntity) {
        this.opinion = opinion;
        this.userEntity = userEntity;
        this.hotelEntity = hotelEntity;
    }
}
