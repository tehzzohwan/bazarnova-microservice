package com.tehzzcode.productservice.controller;

import com.tehzzcode.productservice.dto.DefaultResponse;
import com.tehzzcode.productservice.dto.ProductRequest;
import com.tehzzcode.productservice.dto.ProductResponse;
import com.tehzzcode.productservice.service.impl.ProductServiceImpl;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DefaultResponse<ProductResponse>> addProduct(@RequestBody ProductRequest productDto) {
        return productServiceImpl.addProduct(productDto);
    }

    @GetMapping("/view-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DefaultResponse<List<ProductResponse>>> getAllProducts() {
        return productServiceImpl.getAllProducts();
    }

}
