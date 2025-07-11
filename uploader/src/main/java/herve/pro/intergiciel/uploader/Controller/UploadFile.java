package herve.pro.intergiciel.uploader.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import herve.pro.intergiciel.uploader.Exceptions.FIleExceptions;

@RestController
@RequestMapping("uploader/")
public class UploadFile {

    @PostMapping(path = "/upload", consumes = "multipart/form-data")
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FIleExceptions("Le fichier est vide");
        }
        try {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            return filePath.toString(); // retourne le chemin complet
        } catch (IOException e) {
            throw new FIleExceptions("Erreur lors de l'upload du fichier : " +
                    e.getMessage());
        }
    }

    @GetMapping("/upload")
    public ResponseEntity<?> getUploadFiles() {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath) || !Files.isDirectory(uploadPath)) {
            return ResponseEntity.notFound().build();
        }

        try {
            List<String> fileNames = Files.list(uploadPath)
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(fileNames);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la lecture du dossier: " + e.getMessage());
        }
    }

    @GetMapping("/upload/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) {
        String uploadDir = "uploads/";
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }
        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

}
