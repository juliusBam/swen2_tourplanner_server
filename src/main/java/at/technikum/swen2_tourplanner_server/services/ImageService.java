package at.technikum.swen2_tourplanner_server.services;

import at.technikum.swen2_tourplanner_server.entities.DbImage;
import at.technikum.swen2_tourplanner_server.repositories.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public DbImage getImageByName(String name) {
        return this.imageRepository.getDbImageByName(name);
    }
}
