package at.technikum.swen2_tourplanner_server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "locations")
public class Location {

    @Id
    @GeneratedValue
    @Column(name = "label_id")
    private Long id;

    @Column(name = "coordinates", nullable = false)
    @NotNull(message = "Location coordinates cannot be null")
    @NotBlank(message = "Location coordinates cannot be empty")
    private String coordinate;

    @Column(name = "stop_label", nullable = false, length = 100)
    @NotNull(message = "Location label cannot be null")
    @NotBlank(message = "Location label cannot be empty")
    @Size(max = 100, message = "A valid Location label must contain less than 100 characters")
    private String label;

    public Location() {}

    public Location(String coordinate, String label) {
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
