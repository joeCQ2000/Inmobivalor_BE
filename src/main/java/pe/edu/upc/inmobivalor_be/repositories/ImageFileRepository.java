package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.inmobivalor_be.entities.Image_inmo;

public interface ImageFileRepository  extends JpaRepository <Image_inmo, Long> {
}
