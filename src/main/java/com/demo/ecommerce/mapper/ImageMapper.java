package com.demo.ecommerce.mapper;

import com.demo.ecommerce.dto.image.ImageResponse;
import com.demo.ecommerce.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageResponse toImageResponse(Image image);
}

