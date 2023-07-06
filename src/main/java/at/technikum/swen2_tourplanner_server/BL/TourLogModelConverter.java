package at.technikum.swen2_tourplanner_server.BL;

import at.technikum.swen2_tourplanner_server.dto.TourLogDto;
import at.technikum.swen2_tourplanner_server.entities.TourLog;

public class TourLogModelConverter {
    public static TourLogDto tourLogEntityToDto(TourLog tourLog) {

        return new TourLogDto(
                tourLog.getId(),
                tourLog.getTimeStamp(),
                tourLog.getComment(),
                tourLog.getDifficulty(),
                tourLog.getTotalTimeMinutes(),
                tourLog.getRating(),
                tourLog.getTourId()
        );
    }

}
