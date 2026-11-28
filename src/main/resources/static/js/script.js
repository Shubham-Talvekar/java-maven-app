
let products = [];
let cart = {};
let category = "All";

const money = amount =>
    "₹" + Number(amount).toLocaleString("en-IN");

const escapeHtml = value =>
    String(value ?? "").replace(/[&<>"']/g, character => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
    }[character]));

function safeImage(url) {
    try {
        const parsed = new URL(url);

        if (parsed.protocol === "https:") {
            return escapeHtml(parsed.href);
        }
    } catch (error) {}

    return "";
}

async function loadProducts() {

    try {

        const response = await fetch("/api/products");

        if (!response.ok) {
            throw new Error("Unable to load products");
        }

        products = await response.json();

        renderProducts();
        updateCart();

    } catch (error) {

        document.getElementById("products").textContent =
            "Unable to connect to the product database.";
    }
}

function renderProducts() {

    const search = document.getElementById("search")
        .value.toLowerCase();

    const sort = document.getElementById("sort").value;

    let filtered = products.filter(product => {

        return (
            category === "All" ||
            product.category === category
        ) && (
            product.name.toLowerCase().includes(search) ||
            product.description.toLowerCase().includes(search)
        );
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
        filtered.map(product => `

            <article class="product">

                <img src="${safeImage(product.image)}"
                     alt="${escapeHtml(product.name)}"
                     loading="lazy">

                <h3>${escapeHtml(product.name)}</h3>

                <p class="description">
                    ${escapeHtml(product.description)}
                </p>

                <span class="rating">
                    ${Number(product.rating)} ★
                </span>

                <div class="price">
                    ${money(product.price)}
                </div>

                <button class="add-button"
                    onclick="addToCart(${Number(product.id)})">
                    🛒 Add to Cart
                </button>

            </article>

        `).join("") || "<p>No products found.</p>";
}

function addToCart(id) {

    cart[id] = Math.min((cart[id] || 0) + 1, 100);

    updateCart();

    showToast("Product added to cart!");
}

function changeQuantity(id, amount) {

    cart[id] = Math.min((cart[id] || 0) + amount, 100);

    if (cart[id] <= 0) {
        delete cart[id];
    }

    updateCart();
}

function updateCart() {

    let total = 0;
    let count = 0;
    let html = "";

    Object.entries(cart).forEach(([id, quantity]) => {

        const product = products.find(
            p => p.id === Number(id)
        );

        if (!product) return;

        total += product.price * quantity;
        count += quantity;

        html += `

            <div class="cart-item">

                <img src="${safeImage(product.image)}"
                     alt="${escapeHtml(product.name)}">

                <div>

                    <strong>
                        ${escapeHtml(product.name)}
                    </strong>

                    <p>${money(product.price * quantity)}</p>

                    <div class="quantity">

                        <button onclick="changeQuantity(${Number(id)}, -1)">
                            −
                        </button>

                        ${quantity}

                        <button onclick="changeQuantity(${Number(id)}, 1)">
                            +
                        </button>

                    </div>

                </div>

            </div>
        `;
    });

    document.getElementById("cart-count").textContent =
        count;

    document.getElementById("cart-total").textContent =
        money(total);

    document.getElementById("cart-items").innerHTML =
        html || "<p>Your cart is empty.</p>";
}

function openCart() {

    document.getElementById("cart-panel")
        .classList.add("open");

    document.getElementById("overlay")
        .classList.add("open");
}

function closeCart() {

    document.getElementById("cart-panel")
        .classList.remove("open");

    document.getElementById("overlay")
        .classList.remove("open");
}

async function placeOrder() {

    if (Object.keys(cart).length === 0) {

        showToast("Your cart is empty!");

        return;
    }

    const items = Object.entries(cart).map(
        ([id, quantity]) => ({
            productId: Number(id),
            quantity: quantity
        })
    );

    const button = document.getElementById("checkout");

    button.disabled = true;

    try {

        const response = await fetch("/api/orders", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(items)
        });

        if (!response.ok) {
            throw new Error("Order failed");
        }

        const result = await response.json();

        cart = {};

        updateCart();
        closeCart();

        showToast(
            result.message + " Total: " + money(result.total)
        );

    } catch (error) {

        showToast("Unable to place order. Please try again.");

    } finally {

        button.disabled = false;
    }
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

document.getElementById("search")
    .addEventListener("input", renderProducts);

document.getElementById("sort")
    .addEventListener("change", renderProducts);

document.querySelectorAll("#categories button")
    .forEach(button => {

        button.addEventListener("click", () => {

            category = button.dataset.category;

            document.querySelectorAll("#categories button")
                .forEach(b => b.classList.remove("active"));

            button.classList.add("active");

            renderProducts();
        });
    });

document.addEventListener("keydown", event => {

    if (event.key === "Escape") {
        closeCart();
    }
});

loadProducts();
