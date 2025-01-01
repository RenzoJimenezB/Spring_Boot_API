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

import static org.springframework.http.HttpStatus.*;

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

    @GetMapping("/image/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws SQLException {
        Image image = imageService.getImageById(id);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{id}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long id, @RequestBody MultipartFile file) {
        try {
            imageService.updateImage(file, id);
            return ResponseEntity.ok(new ApiResponse("Update success", null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            log.error("Unexpected error while updating image with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Update failed", null));
        }
    }

    @DeleteMapping("/image/{id}/delete/")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long id) {
        try {
            imageService.deleteImageById(id);
            return ResponseEntity.ok(new ApiResponse("Delete success", null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            log.error("Unexpected error while deleting image with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Delete failed", null));
        }
    }
}
