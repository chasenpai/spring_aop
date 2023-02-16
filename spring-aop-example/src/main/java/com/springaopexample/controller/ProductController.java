package com.springaopexample.controller;

import com.springaopexample.annotation.Delete;
import com.springaopexample.dto.ProductDto;
import com.springaopexample.response.ProductAssembler;
import com.springaopexample.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/example", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    private final ProductAssembler productAssembler;

    @GetMapping("/v1/products/{id}")
    public EntityModel<ProductDto> getProduct(@PathVariable("id") Long id){
        return productAssembler.toModel(productService.getProduct(id));
    }

    @GetMapping("/v1/products")
    public CollectionModel<EntityModel<ProductDto>> getAllProducts(){
        List<EntityModel<ProductDto>> response = productService.getAllProducts()
                .stream()
                .map(productAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(response, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }

    @Delete
    @DeleteMapping("/v1/products/{id}")
    public EntityModel deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return EntityModel.of(
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list")
        );
    }


}
