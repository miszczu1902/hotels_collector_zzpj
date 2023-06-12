package pl.lodz.p.it.zzpj.hotelscollector.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.AddOpinionDTO;
import pl.lodz.p.it.zzpj.hotelscollector.user.service.OpinionService;

import static pl.lodz.p.it.zzpj.hotelscollector.utils.Constans.URI_USERS;

@RequiredArgsConstructor
@RestController
@RequestMapping(URI_USERS)
public class OpinionController {

    private final OpinionService opinionService;

    @PostMapping("/add-opinion/{hotelId}")
    public ResponseEntity<Void> addUser(@PathVariable("hotelId") String hotelId, @RequestBody AddOpinionDTO addOpinionDTO) {
            opinionService.addOpinion(addOpinionDTO.opinion(),hotelId);
            return ResponseEntity.ok().build();
    }

}

