package pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions;

import java.util.List;

public record ResponseBodyErrors(List<SingleErrorDTO> errors) {
}

