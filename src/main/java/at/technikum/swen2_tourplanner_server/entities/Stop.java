package at.technikum.swen2_tourplanner_server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "stops")
public class Stop {

    @Id
    @GeneratedValue
    @Column(name = "label_id")
    private Long id;

    @Column(name = "coordinates", nullable = false)
    @NotNull(message = "Stop coordinates cannot be null")
    @NotBlank(message = "Stop coordinates cannot be empty")
    private String coordinate;

    @Column(name = "stop_label", nullable = false)
    @NotNull(message = "Stop label cannot be null")
    @NotBlank(message = "Stop label cannot be empty")
    private String label;

    public Stop() {}

    public Stop(String coordinate, String label) {
        this.coordinate = coordinate;
        this.label = label;
    }

    //region getters
    public Long getId() {
        return id;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public String getLabel() {
        return label;
    }
    //endregion
}
