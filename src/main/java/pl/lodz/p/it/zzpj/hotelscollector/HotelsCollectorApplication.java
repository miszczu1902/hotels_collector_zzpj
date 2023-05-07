package pl.lodz.p.it.zzpj.hotelscollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.lodz.p.it.zzpj.hotelscollector.security.jwt.RsaKeyProperties;

@EnableScheduling
@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class HotelsCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelsCollectorApplication.class, args);
	}

}
