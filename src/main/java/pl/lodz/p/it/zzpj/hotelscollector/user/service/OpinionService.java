package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.HotelRepository;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.OpinionEntity;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OpinionService {

    @Autowired
    private HttpServletRequest request;

    private final OpinionRepository opinionRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addOpinion(String opinion, String hotelId) {
        final String username = request.getUserPrincipal().getName();
        var user = userRepository.findByUsername(username);
        var hotel = hotelRepository.findById(hotelId);
        opinionRepository.save(new OpinionEntity(opinion, user.get(), hotel.get()));
    }

}
