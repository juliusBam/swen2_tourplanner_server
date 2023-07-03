package at.technikum.swen2_tourplanner_server.restServer.services.interfaces;

import at.technikum.swen2_tourplanner_server.dto.TourLogDto;
import at.technikum.swen2_tourplanner_server.dto.responses.TourLogManipulationResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ITourLogService {
    List<TourLogDto> getAllByTourId(Long id);

    @Transactional
    TourLogManipulationResponseDto createTourLog(TourLogDto newTourLogReq);

    @Transactional
    TourLogManipulationResponseDto updateTourLog(TourLogDto updatedTourLog);

    @Transactional
    TourLogManipulationResponseDto deleteTourLog(Long tourLogId);
}
