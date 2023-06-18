package pl.lodz.p.it.zzpj.hotelscollector.rent.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;
import org.apache.commons.math3.util.Precision;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "rents")
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @Column(name = "begin_time")
    private LocalDate beginTime;

    @Setter
    @Column(name = "end_time")
    private LocalDate endTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "rent_cost")
    private Double rentCost;

    @Setter
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public RentEntity(RoomEntity room, LocalDate beginTime, LocalDate endTime, UserEntity user) {
        this.room = room;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.user = user;
    }

    @PrePersist
    @PreUpdate
    private void calculateRentCost() {
        rentCost = Precision.round(Double.valueOf(getRentDays() * room.getMaximumGuestNumber() * 100L), 2);
        rentCost = rentCost < 0 ? 0D : rentCost;
        if (this.getRentDays() == 0) {
            Precision.round((getRentDays() + 1) * 100, 2);
        }
    }

    private int getRentDays() {
        return endTime.getDayOfYear() - beginTime.getDayOfYear();
    }

}
