package pl.lodz.p.it.zzpj.hotelscollector.user.mappers;

import pl.lodz.p.it.zzpj.hotelscollector.user.dto.OpinionForListDTO;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.OpinionEntity;

public class OpinionMapper {

    public static OpinionForListDTO opinionToOpinionForListDTO(OpinionEntity opinionEntity) {
        return new OpinionForListDTO(
                opinionEntity.getUserEntity().getUsername(),
                opinionEntity.getHotelEntity().getName(),
                opinionEntity.getOpinion()
        );
    }
}
