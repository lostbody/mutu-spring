package gr.aueb.cf.mutu.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageLoader {
    public static String loadImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            byte[] fileBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            System.out.println("failed to load image: " + imagePath);
            return null;
        }
    }
}
