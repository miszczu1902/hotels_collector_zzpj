package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.OpinionEntity;


public interface OpinionRepository extends JpaRepository<OpinionEntity, String> {

}