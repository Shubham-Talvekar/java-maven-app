
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    @GetMapping(value = "/", produces = "text/html;charset=UTF-8")
    public String home() {

        return """
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ShopKart - Online Shopping</title>

<style>

* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

body {
    font-family: Arial, sans-serif;
    background: #f1f3f6;
    color: #172337;
}

button, input, select {
    font: inherit;
}

button {
    cursor: pointer;
}

header {
    background: #2874f0;
    color: white;
    padding: 18px 5%;
    display: flex;
    align-items: center;
    gap: 25px;
    position: sticky;
    top: 0;
    z-index: 100;
    flex-wrap: wrap;
    box-shadow: 0 3px 12px #0002;
}

.logo {
    font-size: 29px;
    font-weight: bold;
    font-style: italic;
}

.logo small {
    display: block;
    font-size: 11px;
    color: #ffe500;
    letter-spacing: 2px;
}

.search {
    flex: 1;
    min-width: 180px;
}

.search input {
    width: 100%;
    padding: 13px 18px;
    border: none;
    border-radius: 7px;
    outline: none;
}

.cart-button {
    background: #ffcf33;
    color: #172337;
    padding: 12px 20px;
    border: none;
    border-radius: 7px;
    font-weight: bold;
}

.categories {
    background: white;
    display: flex;
    justify-content: center;
    gap: 12px;
    padding: 18px;
    flex-wrap: wrap;
    box-shadow: 0 2px 5px #0001;
}

.category {
    padding: 12px 22px;
    border: 1px solid #e4e7ec;
    border-radius: 9px;
    background: white;
    transition: 0.2s;
}

.category:hover,
.category.active {
    background: #2874f0;
    color: white;
    border-color: #2874f0;
}

.hero {
    margin: 22px auto;
    width: 94%;
    padding: 65px 6%;
    border-radius: 18px;
    background: linear-gradient(120deg, #102e75, #2874f0, #00a8e8);
    color: white;
    position: relative;
    overflow: hidden;
}

.hero::after {
    content: "🛍️";
    position: absolute;
    right: 8%;
    top: 15%;
    font-size: 140px;
    opacity: 0.35;
    pointer-events: none;
}

.hero h1 {
    font-size: clamp(30px, 5vw, 54px);
    margin: 15px 0;
}

.hero p {
    font-size: 19px;
    margin-bottom: 25px;
}

.hero button {
    background: #ffcf33;
    color: #172337;
    border: none;
    padding: 14px 28px;
    border-radius: 8px;
    font-weight: bold;
}

.sale-label {
    display: inline-block;
    background: #ffffff25;
    padding: 8px 15px;
    border-radius: 20px;
}

.benefits {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    gap: 15px;
    padding: 22px;
    background: white;
    margin: 20px 3%;
    border-radius: 12px;
    font-weight: bold;
}

.section {
    padding: 20px 3% 40px;
}

.section-heading {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    gap: 15px;
    margin-bottom: 22px;
}

.section-heading select {
    padding: 10px;
    border: 1px solid #ddd;
    background: white;
    border-radius: 7px;
}

.products {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
    gap: 22px;
}

.product {
    background: white;
    padding: 18px;
    border-radius: 12px;
    position: relative;
    box-shadow: 0 3px 12px #00000009;
    transition: transform 0.25s, box-shadow 0.25s;
}

.product:hover {
    transform: translateY(-6px);
    box-shadow: 0 12px 25px #0002;
}

.product img {
    width: 100%;
    height: 210px;
    object-fit: cover;
    border-radius: 8px;
    background: #eef2f7;
}

.discount {
    position: absolute;
    top: 28px;
    left: 28px;
    background: #16863b;
    color: white;
    padding: 6px 10px;
    border-radius: 5px;
    font-size: 12px;
}

.product h3 {
    font-size: 17px;
    margin: 16px 0 10px;
}

.description {
    color: #777;
    font-size: 13px;
    margin-bottom: 12px;
}

.rating {
    display: inline-block;
    background: #388e3c;
    color: white;
    padding: 4px 8px;
    border-radius: 5px;
    font-size: 12px;
}

.price {
    font-size: 23px;
    font-weight: bold;
    margin-top: 14px;
}

.old-price {
    font-size: 13px;
    color: #888;
    text-decoration: line-through;
    margin-left: 8px;
}

.add-button {
    width: 100%;
    background: #ff9f00;
    color: white;
    border: none;
    padding: 12px;
    margin-top: 16px;
    border-radius: 7px;
    font-weight: bold;
}

.add-button:hover {
    background: #e58e00;
}

.overlay {
    position: fixed;
    inset: 0;
    background: #0007;
    z-index: 199;
    display: none;
}

.overlay.open {
    display: block;
}

.cart-panel {
    position: fixed;
    right: -460px;
    top: 0;
    width: min(440px, 100vw);
    height: 100vh;
    background: white;
    z-index: 200;
    transition: right 0.3s;
    display: flex;
    flex-direction: column;
    box-shadow: -5px 0 25px #0002;
}

.cart-panel.open {
    right: 0;
}

.cart-header {
    background: #2874f0;
    color: white;
    padding: 22px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.close-button {
    border: none;
    background: transparent;
    color: white;
    font-size: 28px;
}

.cart-items {
    padding: 20px;
    flex: 1;
    overflow-y: auto;
}

.cart-item {
    display: flex;
    gap: 12px;
    border-bottom: 1px solid #eee;
    padding: 15px 0;
}

.cart-item img {
    width: 75px;
    height: 75px;
    object-fit: cover;
    border-radius: 7px;
}

.cart-details {
    flex: 1;
}

.quantity {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-top: 10px;
}

.quantity button {
    border: 1px solid #ddd;
    background: #f5f5f5;
    width: 30px;
    height: 30px;
    border-radius: 5px;
}

.cart-footer {
    padding: 22px;
    border-top: 1px solid #ddd;
}

.total {
    display: flex;
    justify-content: space-between;
    font-size: 22px;
    font-weight: bold;
    margin-bottom: 20px;
}

.checkout {
    width: 100%;
    background: #fb641b;
    color: white;
    padding: 15px;
    border: none;
    border-radius: 7px;
    font-weight: bold;
}

.toast {
    position: fixed;
    bottom: 30px;
    left: 50%;
    transform: translateX(-50%);
    background: #172337;
    color: white;
    padding: 15px 25px;
    border-radius: 10px;
    z-index: 300;
    display: none;
    max-width: 90vw;
    text-align: center;
}

.empty {
    text-align: center;
    color: #888;
    padding: 40px 15px;
}

footer {
    background: #172337;
    color: white;
    text-align: center;
    padding: 35px;
    line-height: 2;
}

footer p {
    color: #b7c5da;
    font-size: 13px;
}

@media (max-width: 600px) {
    header {
        gap: 12px;
    }

    .search {
        order: 3;
        flex-basis: 100%;
    }

    .products {
        grid-template-columns: repeat(2, minmax(0, 1fr));
        gap: 10px;
    }

    .product {
        padding: 12px;
    }

    .product img {
        height: 150px;
    }

    .price {
        font-size: 18px;
    }

    .hero::after {
        display: none;
    }

    .category {
        padding: 9px 12px;
    }
}

</style>
</head>

<body>

<header>

    <div class="logo">
        ShopKart
        <small>EXPLORE MORE</small>
    </div>

    <div class="search">
        <input
            id="search"
            type="search"
            placeholder="Search for products, brands and more..."
            oninput="renderProducts()">
    </div>

    <button class="cart-button" onclick="openCart()">
        🛒 Cart (<span id="cart-count">0</span>)
    </button>

</header>

<nav class="categories">

    <button class="category active"
        onclick="filterCategory('All', this)">
        🏪 All Products
    </button>

    <button class="category"
        onclick="filterCategory('Mobiles', this)">
        📱 Mobiles
    </button>

    <button class="category"
        onclick="filterCategory('Electronics', this)">
        💻 Electronics
    </button>

    <button class="category"
        onclick="filterCategory('Fashion', this)">
        👕 Fashion
    </button>

    <button class="category"
        onclick="filterCategory('Home', this)">
        🏠 Home
    </button>

</nav>

<section class="hero">

    <span class="sale-label">
        ⚡ THE BIG SHOPPING FESTIVAL
    </span>

    <h1>Big Deals. Bigger Savings.</h1>

    <p>Discover amazing products with up to 70% off.</p>

    <button onclick="document.getElementById('shop').scrollIntoView({behavior:'smooth'})">
        Shop Now →
    </button>

</section>

<div class="benefits">
    <span>🚚 Free Delivery</span>
    <span>🔒 Secure Shopping</span>
    <span>↩️ Easy Returns</span>
    <span>🎧 Customer Support</span>
</div>

<section class="section" id="shop">

    <div class="section-heading">

        <div>
            <h2>Top Deals For You</h2>
            <small id="product-count"></small>
        </div>

        <select id="sort" onchange="renderProducts()">
            <option value="default">Recommended</option>
            <option value="low">Price: Low to High</option>
            <option value="high">Price: High to Low</option>
            <option value="rating">Top Rated</option>
        </select>

    </div>

    <div class="products" id="products"></div>

</section>

<div class="overlay" id="overlay" onclick="closeCart()"></div>

<aside class="cart-panel" id="cart-panel">

    <div class="cart-header">
        <h2>🛒 My Shopping Cart</h2>
        <button class="close-button" onclick="closeCart()">×</button>
    </div>

    <div class="cart-items" id="cart-items"></div>

    <div class="cart-footer">

        <div class="total">
            <span>Total</span>
            <span id="cart-total">₹0</span>
        </div>

        <button class="checkout" onclick="checkout()">
            Place Order
        </button>

    </div>

</aside>

<div class="toast" id="toast"></div>

<footer>

    <h2>ShopKart</h2>

    <p>Your favourite online shopping destination.</p>

    <p>About Us | Contact Us | Privacy Policy | Help Centre</p>

    <p>Powered by Java Spring Boot, Jenkins and Docker</p>

    <p>Demo e-commerce project — No real transactions</p>

</footer>

<script>

const products = [

    {
        id: 1,
        name: "Premium Smartphone",
        category: "Mobiles",
        description: "128GB Storage | AMOLED Display",
        price: 24999,
        oldPrice: 34999,
        rating: 4.6,
        image: "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500"
    },

    {
        id: 2,
        name: "Ultra Slim Laptop",
        category: "Electronics",
        description: "16GB RAM | 512GB SSD",
        price: 54999,
        oldPrice: 74999,
        rating: 4.7,
        image: "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=500"
    },

    {
        id: 3,
        name: "Wireless Headphones",
        category: "Electronics",
        description: "Noise Cancellation | Bluetooth",
        price: 2999,
        oldPrice: 5999,
        rating: 4.4,
        image: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500"
    },

    {
        id: 4,
        name: "Premium Smartwatch",
        category: "Electronics",
        description: "Fitness Tracking | Heart Rate",
        price: 3499,
        oldPrice: 6999,
        rating: 4.3,
        image: "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500"
    },

    {
        id: 5,
        name: "Running Sneakers",
        category: "Fashion",
        description: "Lightweight | Everyday Comfort",
        price: 1999,
        oldPrice: 3999,
        rating: 4.5,
        image: "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500"
    },

    {
        id: 6,
        name: "Classic Cotton T-Shirt",
        category: "Fashion",
        description: "Premium Cotton | Regular Fit",
        price: 699,
        oldPrice: 1499,
        rating: 4.2,
        image: "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500"
    },

    {
        id: 7,
        name: "Modern Lounge Chair",
        category: "Home",
        description: "Premium Finish | Comfortable Design",
        price: 7999,
        oldPrice: 12999,
        rating: 4.6,
        image: "https://images.unsplash.com/photo-1567538096630-e0c55bd6374c?w=500"
    },

    {
        id: 8,
        name: "Professional Camera",
        category: "Electronics",
        description: "4K Video | High Resolution",
        price: 45999,
        oldPrice: 59999,
        rating: 4.8,
        image: "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=500"
    },

    {
        id: 9,
        name: "Premium Travel Backpack",
        category: "Fashion",
        description: "Water Resistant | Laptop Compartment",
        price: 1499,
        oldPrice: 2999,
        rating: 4.4,
        image: "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500"
    },

    {
        id: 10,
        name: "Decorative Table Lamp",
        category: "Home",
        description: "Modern Design | Warm Lighting",
        price: 1299,
        oldPrice: 2499,
        rating: 4.3,
        image: "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=500"
    }

];

let selectedCategory = "All";
let cart = {};

try {
    const saved = JSON.parse(localStorage.getItem("shopkart-cart") || "{}");
    products.forEach(p => {
        if (Number.isInteger(saved[p.id]) && saved[p.id] > 0) {
            cart[p.id] = saved[p.id];
        }
    });
} catch (error) {
    cart = {};
}

const money = amount => "₹" + amount.toLocaleString("en-IN");

function filterCategory(category, button) {

    selectedCategory = category;

    document.querySelectorAll(".category").forEach(item => {
        item.classList.remove("active");
    });

    button.classList.add("active");

    renderProducts();
}

function renderProducts() {

    const search = document.getElementById("search")
        .value.toLowerCase();

    const sort = document.getElementById("sort").value;

    let filtered = products.filter(product => {

        const categoryMatch =
            selectedCategory === "All" ||
            product.category === selectedCategory;

        const searchMatch =
            product.name.toLowerCase().includes(search) ||
            product.description.toLowerCase().includes(search) ||
            product.category.toLowerCase().includes(search);

        return categoryMatch && searchMatch;
    });

    if (sort === "low") {
        filtered.sort((a, b) => a.price - b.price);
    }

    if (sort === "high") {
        filtered.sort((a, b) => b.price - a.price);
    }

    if (sort === "rating") {
        filtered.sort((a, b) => b.rating - a.rating);
    }

    document.getElementById("product-count").textContent =
        filtered.length + " products available";

    document.getElementById("products").innerHTML =
        filtered.map(product => {

            const discount = Math.round(
                (1 - product.price / product.oldPrice) * 100
            );

            return `
                <article class="product">

                    <span class="discount">
                        ${discount}% OFF
                    </span>

                    <img src="${product.image}"
                         alt="${product.name}"
                         loading="lazy">

                    <h3>${product.name}</h3>

                    <p class="description">
                        ${product.description}
                    </p>

                    <span class="rating">
                        ${product.rating} ★
                    </span>

                    <div class="price">
                        ${money(product.price)}

                        <span class="old-price">
                            ${money(product.oldPrice)}
                        </span>
                    </div>

                    <button class="add-button"
                        onclick="addToCart(${product.id})">
                        🛒 Add to Cart
                    </button>

                </article>
            `;

        }).join("");

    if (filtered.length === 0) {
        document.getElementById("products").innerHTML =
            '<div class="empty">No matching products found.</div>';
    }
}

function addToCart(id) {

    cart[id] = (cart[id] || 0) + 1;

    updateCart();

    showToast("Product added to your cart!");
}

function changeQuantity(id, amount) {

    cart[id] = (cart[id] || 0) + amount;

    if (cart[id] <= 0) {
        delete cart[id];
    }

    updateCart();
}

function updateCart() {

    try {
        localStorage.setItem("shopkart-cart", JSON.stringify(cart));
    } catch (error) {
        // Cart remains available for the current session.
    }

    let total = 0;
    let count = 0;
    let html = "";

    Object.entries(cart).forEach(([id, quantity]) => {

        const product = products.find(p => p.id === Number(id));

        if (!product) return;

        total += product.price * quantity;
        count += quantity;

        html += `
            <div class="cart-item">

                <img src="${product.image}" alt="${product.name}">

                <div class="cart-details">

                    <strong>${product.name}</strong>

                    <p>${money(product.price * quantity)}</p>

                    <div class="quantity">

                        <button onclick="changeQuantity(${id}, -1)">
                            −
                        </button>

                        <span>${quantity}</span>

                        <button onclick="changeQuantity(${id}, 1)">
                            +
                        </button>

                    </div>

                </div>

            </div>
        `;

    });

    document.getElementById("cart-count").textContent = count;

    document.getElementById("cart-total").textContent =
        money(total);

    document.getElementById("cart-items").innerHTML =
        html || '<div class="empty">🛒 Your cart is empty.</div>';
}

function openCart() {

    document.getElementById("cart-panel").classList.add("open");

    document.getElementById("overlay").classList.add("open");
}

function closeCart() {

    document.getElementById("cart-panel").classList.remove("open");

    document.getElementById("overlay").classList.remove("open");
}

let toastTimer;

function showToast(message) {

    const toast = document.getElementById("toast");

    toast.textContent = message;
    toast.style.display = "block";

    clearTimeout(toastTimer);

    toastTimer = setTimeout(() => {
        toast.style.display = "none";
    }, 3000);
}

function checkout() {

    if (Object.keys(cart).length === 0) {

        showToast("Your cart is empty!");

        return;
    }

    cart = {};

    updateCart();

    closeCart();

    showToast("Demo order placed successfully! Thank you.");
}

document.addEventListener("keydown", event => {
    if (event.key === "Escape") {
        closeCart();
    }
});

renderProducts();
updateCart();

</script>

</body>
</html>
        """;
    }
}
