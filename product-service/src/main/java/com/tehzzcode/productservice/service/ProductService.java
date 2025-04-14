package com.tehzzcode.productservice.service;

import com.tehzzcode.productservice.dto.DefaultResponse;
import com.tehzzcode.productservice.dto.ProductRequest;
import com.tehzzcode.productservice.dto.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
     ResponseEntity<DefaultResponse<ProductResponse>> addProduct(ProductRequest productDto);

     ResponseEntity<DefaultResponse<List<ProductResponse>>> getAllProducts();
}
