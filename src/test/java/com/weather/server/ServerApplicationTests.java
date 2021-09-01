package com.weather.server;

import com.weather.server.domain.repository.MeasureRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class ServerApplicationTests {
	@Mock
	MeasureRepository measureRepository;

	@Test
	void contextLoads() {
	}


	@Test
	void readMongo(){
		System.out.println("Test");
		//System.out.println(measureRepository.findAllOrderByTimestampAsc());
		System.out.println(measureRepository.findByTemp("24.37"));
	}
}
