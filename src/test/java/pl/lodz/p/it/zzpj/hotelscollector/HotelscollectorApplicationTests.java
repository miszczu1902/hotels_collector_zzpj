package pl.lodz.p.it.zzpj.hotelscollector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class HotelscollectorApplicationTests {

	@Test
	void shouldTestExampleMethod() {
		SumCalculator sumCalculator = new SumCalculator();
		assertThat(sumCalculator.sumCalculator(1,5)).isEqualTo(6);

	}

	@Test
	void contextLoads() {
	}

}
