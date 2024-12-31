package com.demo.ecommerce.controller;

import com.demo.ecommerce.dto.ImageDto;
import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.model.Image;
import com.demo.ecommerce.response.ApiResponse;
import com.demo.ecommerce.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDto> imagesDto = imageService.addImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success", imagesDto));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Upload failed", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            imageService.updateImage(file, imageId);
            return ResponseEntity.ok(new ApiResponse("Update success", null));

        } catch (ResourceNotFoundException e) {
            log.info("Image with ID {} not found: {}", imageId, e.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Image not found for ID: " + imageId, null));

        } catch (Exception e) {
            log.error("Unexpected error while updating image with ID {}: {}", imageId, e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", null));
        }
    }
}
