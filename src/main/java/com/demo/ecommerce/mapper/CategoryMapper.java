package com.demo.ecommerce.mapper;

import com.demo.ecommerce.dto.category.CategoryResponse;
import com.demo.ecommerce.model.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {


    CategoryResponse toCategoryResponse(Category category);
}
