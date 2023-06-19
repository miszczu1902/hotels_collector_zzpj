package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.HotelRepository;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.OpinionEntity;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.HotelDoesntexistsException;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.OpinionAlreadyExistsException;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.OpinionsDoesntExistException;

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
    public void addOpinion(String opinionText, String hotelId) {
        final String username = request.getUserPrincipal().getName();
        var user = userRepository.findByUsername(username);
        var hotel = hotelRepository.findById(hotelId);
        if (hotel.isEmpty()) {
            log.warn("Hotel doesnt exist");
            throw new HotelDoesntexistsException("Hotel doesnt exist");
        }
        final var opinion = opinionRepository.findOpinionEntitiesByUserEntity(user);
        if (opinion.isPresent()) {
            log.warn("You have already added opinion about this hotel");
            throw new OpinionAlreadyExistsException("You have already added opinion about this hotel");
        }
        opinionRepository.save(new OpinionEntity(opinionText, user.get(), hotel.get()));
    }

    @Transactional
    public List<OpinionEntity> getAllOpinions() {
        final var opinionList = opinionRepository.findAll();
        if (opinionList.isEmpty()) {
            log.warn("there are no opinions in database");
            throw new OpinionsDoesntExistException("there are no opinions in database");
        }
        return opinionList;
    }

    @Transactional
    public void modifyOpinion(String opinionText, String hotelId) {
        final String username = request.getUserPrincipal().getName();
        final var user = userRepository.findByUsername(username);
        final var hotel = hotelRepository.findById(hotelId);
        if (hotel.isEmpty()) {
            log.warn("Hotel doesnt exist");
            throw new HotelDoesntexistsException("Hotel doesnt exist");
        }
        final var opinion = opinionRepository.findOpinionEntitiesByUserEntity(user);
        if (opinion.isEmpty()) {
            log.warn("You did not add opinion about this hotel");
            throw new OpinionsDoesntExistException("You did not add opinion about this hotel");
        }
        opinion.get().setOpinion(opinionText);
        opinionRepository.save(opinion.get());
    }
}
