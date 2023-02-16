package com.springaopexample.service;

import com.springaopexample.dto.ProductDto;
import com.springaopexample.entity.Product;
import com.springaopexample.exception.ProductNotFoundException;
import com.springaopexample.repository.ProductRepository;
import com.springaopexample.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto getProduct(Long id){

        Optional<Product> product = productRepository.findById(id);
        throw new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
//        if(product.isPresent()){
//            return new ProductDto(product.get());
//        }else{
//            throw new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
//        }

    }

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}
