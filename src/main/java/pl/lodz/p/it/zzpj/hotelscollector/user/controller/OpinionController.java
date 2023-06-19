package pl.lodz.p.it.zzpj.hotelscollector.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.AddOpinionDTO;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.OpinionForListDTO;
import pl.lodz.p.it.zzpj.hotelscollector.user.mappers.OpinionMapper;
import pl.lodz.p.it.zzpj.hotelscollector.user.service.OpinionService;

import java.util.List;

import static pl.lodz.p.it.zzpj.hotelscollector.utils.Constans.URI_USERS;

@RequiredArgsConstructor
@RestController
@RequestMapping(URI_USERS)
public class OpinionController {

    private final OpinionService opinionService;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/add-opinion/{hotelId}")
    public ResponseEntity<Void> addOpinion(@PathVariable("hotelId") String hotelId, @RequestBody AddOpinionDTO addOpinionDTO) {
            opinionService.addOpinion(addOpinionDTO.opinion(),hotelId);
            return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/modify-opinion/{hotelId}")
    public ResponseEntity<Void> modifyOpinion(@PathVariable("hotelId") String hotelId, @RequestBody AddOpinionDTO addOpinionDTO) {
            opinionService.modifyOpinion(addOpinionDTO.opinion(),hotelId);
            return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/get-all-opinions")
    public ResponseEntity<List<OpinionForListDTO>> getAllOpinions() {
        final var responseBody = opinionService.getAllOpinions().stream().map(OpinionMapper::opinionToOpinionForListDTO).toList();
        return ResponseEntity.ok(responseBody);
    }
}

