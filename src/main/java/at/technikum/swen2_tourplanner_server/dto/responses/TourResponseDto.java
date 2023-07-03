package at.technikum.swen2_tourplanner_server.dto.responses;

import at.technikum.swen2_tourplanner_server.dto.TourDto;
import at.technikum.swen2_tourplanner_server.dto.TourStatsDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourResponseDto {

    private TourStatsDto tourStats;

    @JsonUnwrapped
    private TourDto tour;

    public TourResponseDto(TourStatsDto tourStats, TourDto tour) {
        this.tourStats = tourStats;
        this.tour = tour;
    }
}
