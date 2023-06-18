package pl.lodz.p.it.zzpj.hotelscollector.rent.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.RoomRepository;
import pl.lodz.p.it.zzpj.hotelscollector.rent.dto.CreateRentDTO;
import pl.lodz.p.it.zzpj.hotelscollector.rent.entity.RentEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.service.UserRepository;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.RentNotExistException;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.RoomNotAvailableException;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.UserDoesntExistException;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.UserIsBlockedException;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RentService {

    private final RentRepository rentRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    public Optional<RentEntity> reserveRoom(CreateRentDTO rent) throws RoomNotAvailableException {
        final UserEntity user = userRepository.findByUsername(rent.username())
                .orElseThrow(() -> new UserDoesntExistException("User doesn't exist"));
        if (user.getIsActive()) {
            final RoomEntity room = roomRepository.findById(String.valueOf(rent.roomId())).orElseThrow();
            final LocalDate beginTime = LocalDate.parse(rent.beginTime());
            final LocalDate endTime = LocalDate.parse(rent.endTime());

            if (rentRepository.findByRoomAndBeginTimeLessThanEqualAndEndTimeGreaterThanEqualAndIsActive(room, beginTime, endTime, true).isEmpty()) {
                return Optional.of(rentRepository.save(new RentEntity(room, beginTime, endTime, user)));
            } else {
                throw new RoomNotAvailableException("Room is not available");
            }
        } else {
            throw new UserIsBlockedException("User is not active");
        }
    }

    public void endReservation(String rentId) throws RentNotExistException {
        final UUID id = UUID.fromString(rentId);
        RentEntity rent = rentRepository.findById(id).orElseThrow();

        if (rentRepository.findByIdAndIsActive(id, false).isPresent()) {
            throw new RentNotExistException("Rent couldn't be ended");
        } else {
            rent.setIsActive(false);

            LocalDate now = LocalDate.now();
            if (rent.getEndTime().isAfter(now)) {
                rent.setEndTime(now);
            }

            rentRepository.save(rent);
        }
    }

    public Optional<RentEntity> getRentById(String rentId) {
        return rentRepository.findById(UUID.fromString(rentId));
    }

    public List<RentEntity> getAllRents() {
        return rentRepository.findAll();
    }

    public List<RentEntity> getAllRentsByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        return rentRepository.findAllByUser(user);
    }

}
