package com.example.fileapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:3000") // XHR
@RestController
@RequestMapping("/files")
public class FileController {

    private final String uploadLocation;

    public FileController(@Value("${upload.location}") String uploadLocation) throws IOException {
        this.uploadLocation = uploadLocation;
        var uploadPath = Paths.get(uploadLocation);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
    }

    @PostMapping
    public List<SaveFileResult> upload(@RequestPart List<MultipartFile> files) {
        return files.stream()
            .map(this::saveFile)
            .collect(Collectors.toList());
    }

    private SaveFileResult saveFile(MultipartFile file) {
        var filename = file.getOriginalFilename();
        var dest = Paths.get(uploadLocation + "/" + filename);
        try {
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return new SaveFileResult().setFilename(filename).setError(true);
        }
        return new SaveFileResult().setFilename(filename);
    }
}
