package at.technikum.swen2_tourplanner_server.dto.responses;

import at.technikum.swen2_tourplanner_server.dto.TourLogDto;
import at.technikum.swen2_tourplanner_server.dto.TourStatsDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourLogManipulationResponseDto {

    @JsonUnwrapped
    private TourLogDto tourLog;


    private TourStatsDto tourStats;

    public TourLogManipulationResponseDto(TourLogDto tourLogDto, TourStatsDto tourStatsDto) {
        this.tourLog = tourLogDto;
        this.tourStats = tourStatsDto;
    }
}
