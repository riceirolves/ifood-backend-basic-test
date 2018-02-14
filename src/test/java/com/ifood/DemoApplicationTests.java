package com.ifood;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ifood.model.WeatherRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	WeatherRepository repository = new WeatherRepository();

	@Test
	public void shouldReturnRightDateMessageTest1() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2014, 9, 10, 6, 40, 55))
				.contains("1 ano."));
	}

	@Test
	public void shouldReturnRightDateMessageTest2() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2014, 10, 10, 6, 40, 55))
				.contains("1 ano e 1 mês."));
	}

	@Test
	public void shouldReturnRightDateMessageTest3() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2014, 10, 11, 6, 40, 55))
				.contains("1 ano, 1 mês e 1 dia."));
	}

	@Test
	public void shouldReturnRightDateMessageTest4() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2014, 10, 11, 7, 40, 55))
				.contains("1 ano, 1 mês, 1 dia e 1 hora."));
	}

	@Test
	public void shouldReturnRightDateMessageTest5() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2014, 10, 11, 7, 41, 55))
				.contains("1 ano, 1 mês, 1 dia, 1 hora e 1 minuto."));
	}

	@Test
	public void shouldReturnRightDateMessageTest6() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2014, 10, 11, 7, 41, 56))
				.contains("1 ano, 1 mês, 1 dia, 1 hora, 1 minuto e 1 segundo."));
	}

	@Test
	public void shouldReturnRightDateMessageTest7() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2013, 10, 11, 7, 41, 56))
				.contains("1 mês, 1 dia, 1 hora, 1 minuto e 1 segundo."));
	}

	@Test
	public void shouldReturnRightDateMessageTest8() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2013, 9, 11, 7, 41, 56))
				.contains("1 dia, 1 hora, 1 minuto e 1 segundo."));
	}

	@Test
	public void shouldReturnRightDateMessageTest9() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2013, 9, 10, 7, 41, 56))
				.contains("1 hora, 1 minuto e 1 segundo."));
	}

	@Test
	public void shouldReturnRightDateMessageTest10() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2013, 9, 10, 6, 41, 56))
				.contains("1 minuto e 1 segundo."));
	}

	@Test
	public void shouldReturnRightDateMessageTest11() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 55), LocalDateTime.of(2013, 9, 10, 6, 40, 56))
				.contains("1 segundo."));
	}

	@Test
	public void shouldReturnRightDateMessageTest12() throws Exception {
		assertThat(repository
				.timeDistance(LocalDateTime.of(2013, 9, 10, 6, 40, 5), LocalDateTime.of(2018, 12, 30, 16, 56, 56))
				.contains("5 anos, 3 meses, 20 dias, 10 horas, 16 minutos e 51 segundos."));
	}

}
