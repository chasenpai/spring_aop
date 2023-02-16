package com.springaopexample.response;

import com.springaopexample.controller.ProductController;
import com.springaopexample.dto.ProductDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler implements RepresentationModelAssembler<ProductDto, EntityModel<ProductDto>> {

    @Override
    public EntityModel<ProductDto> toModel(ProductDto entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ProductController.class).getProduct(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list")
        );
    }

    @Override
    public CollectionModel<EntityModel<ProductDto>> toCollectionModel(Iterable<? extends ProductDto> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

}
