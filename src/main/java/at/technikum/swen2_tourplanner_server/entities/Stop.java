package at.technikum.swen2_tourplanner_server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @Column(name = "stop_label", nullable = false, length = 100)
    @NotNull(message = "Stop label cannot be null")
    @NotBlank(message = "Stop label cannot be empty")
    @Size(max = 100, message = "A valid stop label must contain less than 100 characters")
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
