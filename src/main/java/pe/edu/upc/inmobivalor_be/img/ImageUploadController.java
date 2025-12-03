package pe.edu.upc.inmobivalor_be.img;
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

    private final ImageFileRepository imageFileRepository;

    public ImageUploadController(ImageFileRepository repo) {
        this.imageFileRepository = repo;
    }

    @GetMapping("/obtener/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageId) {
        try {
            // Buscar la imagen en la base de datos
            Image_inmo image = imageFileRepository.findById(imageId)
                    .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));

            // Obtener el nombre del archivo de la base de datos
            String fileName = image.getFileName();

            // Usar el mismo directorio de almacenamiento de imágenes que se usó en el POST
            String uploadDir = "C:/uploads/images";  // Este es el mismo nombre que en el POST
            Path filePath = Paths.get(uploadDir + "/" + fileName);  // Ruta completa

            // Crear un recurso (UrlResource) para la imagen
            Resource resource = new UrlResource(filePath.toUri());

            // Verificar si el archivo existe y es legible
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)  // O puedes ajustar según el tipo de imagen
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si no se encuentra, 404
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Si ocurre un error, 500
        }
    }



    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Image_inmo>> uploadImages(
            @RequestPart("files") MultipartFile[] files
    ) {
        String uploadDir = "C:/uploads/images";
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


