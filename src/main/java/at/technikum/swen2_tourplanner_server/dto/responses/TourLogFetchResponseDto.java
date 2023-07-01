package at.technikum.swen2_tourplanner_server.dto.responses;

import at.technikum.swen2_tourplanner_server.BL.TourLogModelConverter;
import at.technikum.swen2_tourplanner_server.dto.TourLogDto;
import at.technikum.swen2_tourplanner_server.dto.TourStatsDto;
import at.technikum.swen2_tourplanner_server.entities.TourLog;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TourLogFetchResponseDto {

    private List<TourLogDto> tourLog;

    private TourStatsDto tourStats;

    public TourLogFetchResponseDto(List<TourLogDto> tourLogs, TourStatsDto tourStatsDto) {
        this.tourLog = tourLogs;
        this.tourStats = tourStatsDto;
    }

}
