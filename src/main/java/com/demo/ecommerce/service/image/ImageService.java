package com.demo.ecommerce.service.image;

import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.model.Image;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.ImageRepository;
import com.demo.ecommerce.response.ApiResponse;
import com.demo.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;


    @Override
    public ApiResponse addImage(String imageKey, Long productId) {
        Product product = productService.getProductEntityById(productId);

        if (product == null)
            throw new ResourceNotFoundException("Product not found");

        Image image = new Image();
        image.setImageKey(imageKey);
        image.setProduct(product);
        Image savedImage = imageRepository.save(image);

        return new ApiResponse("Success", savedImage);
    }


    @Override
    public ApiResponse getImagesByProductId(Long productId) {
        List<Image> images = imageRepository.findByProductId(productId);
        return new ApiResponse("Success", images);
    }


    @Override
    public ApiResponse deleteImage(String imageKey) {
        return imageRepository.findByImageKey(imageKey)
                .map(image -> new ApiResponse("Delete success", imageKey))
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }
}
