package com.demo.ecommerce.mapper;

import com.demo.ecommerce.dto.ProductPage;
import com.demo.ecommerce.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductPageMapper {

    @Mapping(source = "number", target = "pageNumber", qualifiedByName = "startPaginationOnOne")
    @Mapping(source = "size", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(target = "content", source = "content")
    ProductPage toProductPage(Page<ProductResponse> productPage);

    @Named("startPaginationOnOne")
    default int startOnOne(int pageNumber) {
        return pageNumber + 1;
    }
}
