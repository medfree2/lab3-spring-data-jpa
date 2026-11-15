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
@RequestMapping("/api/categories")
public class CategoryController {
    private final CatalogService service;

    public CategoryController(CatalogService service) {
        this.service = service;
    }

    @GetMapping
    List<CategoryDto> list() {
        return service.categories();
    }

    @GetMapping("/{id}")
    CategoryDto get(@PathVariable Long id) {
        return service.getCategory(id);
    }

    @GetMapping("/{id}/products")
    Page<ProductDto> products(@PathVariable Long id, @PageableDefault(sort = "id") Pageable page) {
        return service.products(id, page);
    }

    @PostMapping
    ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryRequest request) {
        CategoryDto category = service.createCategory(request);
        return ResponseEntity.created(URI.create("/api/categories/" + category.id())).body(category);
    }
}
