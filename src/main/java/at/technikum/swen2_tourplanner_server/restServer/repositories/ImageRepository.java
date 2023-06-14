package at.technikum.swen2_tourplanner_server.restServer.repositories;

import at.technikum.swen2_tourplanner_server.entities.DbImage;
import org.hibernate.mapping.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//todo delete deprecated

public interface ImageRepository extends JpaRepository<DbImage, Long> {

    DbImage getDbImageByName(String name);

}
