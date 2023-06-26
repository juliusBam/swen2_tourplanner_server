package at.technikum.swen2_tourplanner_server.restServer.services.interfaces;

import at.technikum.swen2_tourplanner_server.dto.CreateTourLogReqModel;
import at.technikum.swen2_tourplanner_server.entities.Tour;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ITourLogService {
    List<TourLog> getAllByTourId(Long id);

    @Transactional
    Tour createTourLog(CreateTourLogReqModel newTourLogReq);

    @Transactional
    Tour updateTourLog(CreateTourLogReqModel updatedTourLog);

    @Transactional
    Tour deleteTourLog(Long tourLogId);
}
