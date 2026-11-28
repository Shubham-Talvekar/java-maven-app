
CREATE TABLE IF NOT EXISTS products (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(500),
    rating DOUBLE

);

CREATE TABLE IF NOT EXISTS orders (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(12,2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (product_id) REFERENCES products(id)

);
