
package com.example;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShopController {

    private final ProductRepository repository;

    public ShopController(ProductRepository repository) {
        this.repository = repository;
    }

    public record OrderItem(
        long productId,
        int quantity
    ) {}

    @GetMapping("/products")
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @PostMapping("/orders")
    public Map<String, Object> placeOrder(
        @RequestBody List<OrderItem> items
    ) {

        BigDecimal total = repository.placeOrder(items);

        return Map.of(
            "message", "Order placed successfully!",
            "total", total
        );
    }
}
