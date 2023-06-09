package at.technikum.swen2_tourplanner_server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity(name = "images")
public class DbImage {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @Column(name = "content_base65", nullable = false)
    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content has to be not blank")
    private String base64Content;

    @Column(name = "image_name", nullable = false, length = 50, unique = true)
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name has to be not blank")
    private String name;

    public DbImage(Long id, String base64Content, String name) {
        this.id = id;
        this.base64Content = base64Content;
        this.name = name;
    }

    public DbImage() {
    }

    //region getters
    public Long getId() {
        return this.id;
    }
    public String getBase64Content() {
        return base64Content;
    }

    public String getName() {
        return this.name;
    }
}
