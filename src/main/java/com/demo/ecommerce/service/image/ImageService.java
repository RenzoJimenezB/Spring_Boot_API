package com.demo.ecommerce.service.image;

import com.demo.ecommerce.exception.AlreadyExistsException;
import com.demo.ecommerce.model.Image;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.ImageRepository;
import com.demo.ecommerce.response.ApiResponse;
import com.demo.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public ResponseEntity<ApiResponse> addImage(String imageKey, Long productId) {
        Product product = productService.getProductEntityById(productId);

        if (product == null)
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Product not found", null));

        Image image = new Image();
        image.setImageKey(imageKey);
        image.setProduct(product);
        Image savedImage = imageRepository.save(image);

        return ResponseEntity.ok(new ApiResponse("Success", savedImage));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteImage(String imageKey) {
        return imageRepository.findByImageKey(imageKey)
                .map(image -> ResponseEntity.ok(new ApiResponse("Delete success", imageKey)))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Image not found", null)));
    }
}
