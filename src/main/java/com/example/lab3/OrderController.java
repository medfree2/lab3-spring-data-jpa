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
@RequestMapping("/api/orders")
public class OrderController {
    private final CatalogService service;

    public OrderController(CatalogService service) {
        this.service = service;
    }

    @GetMapping
    Page<OrderDto> list(@PageableDefault(sort = "id") Pageable page) {
        return service.orders(page);
    }

    @GetMapping("/{id}")
    OrderDto get(@PathVariable Long id) {
        return service.getOrder(id);
    }

    @PostMapping
    ResponseEntity<OrderDto> create(@Valid @RequestBody OrderRequest request) {
        OrderDto order = service.createOrder(request);
        return ResponseEntity.created(URI.create("/api/orders/" + order.id())).body(order);
    }
}
