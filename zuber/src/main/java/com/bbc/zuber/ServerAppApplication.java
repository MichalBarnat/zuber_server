package com.bbc.zuber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerAppApplication.class, args);
	}

	//TODO 30.11.2023
	// kafka serializacja i deserializacja
	// dodac LOGGER Slf4j
	// lokalizacja drivera + oszacowac kto najlepszy + koszt + eta
	// liqubase testowe dane
	// testy jednostkowe
	// kafdrop - tez przez deocker compose gdzies wygooglowac
}