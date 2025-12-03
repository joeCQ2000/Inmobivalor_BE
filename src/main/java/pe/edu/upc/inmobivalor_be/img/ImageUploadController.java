package pe.edu.upc.inmobivalor_be.img;
import org.springframework.beans.factory.annotation.Value;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.inmobivalor_be.entities.Image_inmo;
import pe.edu.upc.inmobivalor_be.repositories.ImageFileRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;




@RequestMapping("api/v1/img")
@RestController
public class ImageUploadController {

    @Value("${app.upload-dir}")
    private String uploadDir;

    private final ImageFileRepository imageFileRepository;

    public ImageUploadController(ImageFileRepository repo) {
        this.imageFileRepository = repo;
    }

    @GetMapping("/obtener/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageId) {
        try {
            Image_inmo image = imageFileRepository.findById(imageId)
                    .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));

            String fileName = image.getFileName();

            Path filePath = Paths.get(uploadDir).resolve(fileName);

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // podrías mejorar esto detectando el tipo
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Image_inmo>> uploadImages(
            @RequestPart("files") MultipartFile[] files
    ) {
        List<Image_inmo> savedList = new ArrayList<>();

        try {
            // Asegura que el directorio exista
            Path uploadPath = Paths.get(uploadDir);
            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {
                String originalName = file.getOriginalFilename();
                String ext = originalName.substring(originalName.lastIndexOf("."));
                String storedName = UUID.randomUUID() + ext;

                // Guarda el archivo físico
                FileUploadUtil.saveFile(uploadDir, storedName, file);

                // Guarda solo el nombre en BD
                Image_inmo img = new Image_inmo();
                img.setFileName(storedName);

                savedList.add(imageFileRepository.save(img));
            }

            return ResponseEntity.ok(savedList);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }



}