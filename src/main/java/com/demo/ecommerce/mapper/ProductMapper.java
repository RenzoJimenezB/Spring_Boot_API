package com.demo.ecommerce.mapper;

import com.demo.ecommerce.dto.product.ProductResponse;
import com.demo.ecommerce.model.Image;
import com.demo.ecommerce.model.Product;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "images", target = "imageUrls", qualifiedByName = "mapImagesToUrls")
    ProductResponse toProductResponse(Product product, @Context String cloudFrontDomain);


    @Named("mapImagesToUrls")
    default List<String> mapImagesToUrls(List<Image> images, @Context String cloudFrontDomain) {
        return images
                .stream()
                .map(image -> String.format("%s/%s", cloudFrontDomain, image.getImageKey()))
                .toList();
    }
}
