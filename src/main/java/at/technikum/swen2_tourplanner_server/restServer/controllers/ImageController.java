package at.technikum.swen2_tourplanner_server.restServer.controllers;

import at.technikum.swen2_tourplanner_server.entities.DbImage;
import at.technikum.swen2_tourplanner_server.restServer.repositories.ImageRepository;
import at.technikum.swen2_tourplanner_server.restServer.services.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//todo delete deprecated

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    private final ImageService imageService;

    ImageController(ImageRepository imageRepository) {
        this.imageService = new ImageService(imageRepository);
    }

    @GetMapping(value = "/{name}")
    DbImage getImageByName(@PathVariable String name) {
        return this.imageService.getImageByName(name);
    }
}