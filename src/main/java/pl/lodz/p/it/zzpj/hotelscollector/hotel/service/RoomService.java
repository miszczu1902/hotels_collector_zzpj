package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public Optional<RoomEntity> getRoomEntityById(String id) {
        return roomRepository.findById(id);
    }
}
