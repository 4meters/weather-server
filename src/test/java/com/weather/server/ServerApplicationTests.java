package com.weather.server;

import com.weather.server.domain.repository.MeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ServerApplicationTests {
	@Mock
	MeasureRepository measureRepository;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void readMongo(){
		System.out.println("Test");
		//System.out.println(measureRepository.findAllOrderByTimestampAsc());
		System.out.println(measureRepository.findLastMeasureByStationId("00000000e34ec9d1",(float)200));
	}
}
