
package com.example;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbc;

    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Product> findAll() {

        return jdbc.query(
            "SELECT * FROM products ORDER BY id",
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getString("image"),
                rs.getDouble("rating")
            )
        );
    }

    @Transactional
    public BigDecimal placeOrder(
        List<ShopController.OrderItem> items
    ) {

        if (items == null || items.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Cart is empty"
            );
        }

        BigDecimal total = BigDecimal.ZERO;

        for (ShopController.OrderItem item : items) {

            if (item == null ||
                item.quantity() < 1 ||
                item.quantity() > 100) {

                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid quantity"
                );
            }

            List<BigDecimal> prices = jdbc.query(
                "SELECT price FROM products WHERE id = ?",
                (rs, rowNum) -> rs.getBigDecimal("price"),
                item.productId()
            );

            if (prices.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid product"
                );
            }

            BigDecimal amount = prices.get(0).multiply(
                BigDecimal.valueOf(item.quantity())
            );

            jdbc.update(
                """
                INSERT INTO orders
                (product_id, quantity, total_price)
                VALUES (?, ?, ?)
                """,
                item.productId(),
                item.quantity(),
                amount
            );

            total = total.add(amount);
        }

        return total;
    }
}
