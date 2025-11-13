package pe.edu.upc.inmobivalor_be.img;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.inmobivalor_be.entities.Image_inmo;
import pe.edu.upc.inmobivalor_be.repositories.ImageFileRepository;

import java.io.IOException;
import java.util.*;


@RequestMapping("api/v1/img")
@RestController
public class ImageUploadController {

    private final ImageFileRepository imageFileRepository;

    public ImageUploadController(ImageFileRepository repo) {
        this.imageFileRepository = repo;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Image_inmo>> uploadImages(
            @RequestPart("files") MultipartFile[] files
    ) {
        String uploadDir = "profileImagesFolder";
        List<Image_inmo> savedList = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String ext = originalName.substring(originalName.lastIndexOf(".")); // ej. .jpg
            String storedName = UUID.randomUUID() + ext;                        // nombre único

            try {
                // Guarda el archivo físico
                String relativePath = FileUploadUtil.saveFile(uploadDir, storedName, file);

                // Guarda SOLO el nombre/ruta en la BD
                Image_inmo img = new Image_inmo();
                img.setFileName(storedName);

                savedList.add(imageFileRepository.save(img));

            } catch (IOException e) {
                return ResponseEntity.status(500).build();
            }
        }

        return ResponseEntity.ok(savedList);
    }

}
