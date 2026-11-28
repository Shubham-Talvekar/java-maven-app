
package com.example;

import java.math.BigDecimal;

public record Product(
    Long id,
    String name,
    String category,
    String description,
    BigDecimal price,
    String image,
    double rating
) {}
