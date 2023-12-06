package com.bbc.zuber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerAppApplication.class, args);
	}

	// 30.11.2023
	// kafka serializacja i deserializacja
	// dodac LOGGER Slf4j
	// lokalizacja drivera + oszacowac kto najblizej + koszt + eta
	// liqubase testowe dane
	// testy jednostkowe
	// kafdrop - tez przez deocker compose gdzies wygooglowac
}

// dostajemy RideInfo o tym ze driver jedzie do nas - stworzyc opcje rezygnacji
// po przejechanej trasie serwer wysyla informacje do usera i drivera o zakonczonym przejezdzie
// na podsawie zakonczonej jazdy mozemy wystawic ocene oraz dac napiwek<optional>
// dla ocen - RideFeedback(uuid ride-assignment(status musi byc accepted),  ocena, napiwek<optional>)
// driver tez ma zbierac wszystkie swoje oceny i ma miec endpoint na swoja srednią ocen
//driver tez moze miec endpoint na sprawdzenie ilosci przejechanych km oraz czasu

//user moze miec endpointy - wydane pieniadze