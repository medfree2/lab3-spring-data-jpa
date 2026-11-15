package com.example.lab3;

import static com.example.lab3.ApiDtos.*;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final CatalogService service;

    public ProductController(CatalogService service) {
        this.service = service;
    }

    @GetMapping
    Page<ProductDto> list(@RequestParam(required = false) Long categoryId, @PageableDefault(sort = "id") Pageable page) {
        return service.products(categoryId, page);
    }

    @GetMapping("/{id}")
    ProductDto get(@PathVariable Long id) {
        return service.getProduct(id);
    }

    @PostMapping
    ResponseEntity<ProductDto> create(@Valid @RequestBody ProductRequest request) {
        ProductDto product = service.createProduct(request);
        return ResponseEntity.created(URI.create("/api/products/" + product.id())).body(product);
    }

    @PutMapping("/{id}")
    ProductDto update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
