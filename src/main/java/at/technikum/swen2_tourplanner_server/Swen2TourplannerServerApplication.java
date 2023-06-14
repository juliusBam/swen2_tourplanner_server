package at.technikum.swen2_tourplanner_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Swen2TourplannerServerApplication {

	//todo think about the usage of versions to avoid overwriting each other changes
	//e.g. fetched record has version x, user can update it only if the stored record has the same
	//version number
	public static void main(String[] args) {
		SpringApplication.run(Swen2TourplannerServerApplication.class, args);
	}

}
