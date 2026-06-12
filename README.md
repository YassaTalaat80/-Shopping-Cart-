# ShopCart — E-Commerce REST API

A production-grade e-commerce backend built with **Spring Boot 3**, **Java 21**, and **PostgreSQL**, featuring JWT-based stateless authentication, role-based access control, full CRUD for products/categories/images, cart management, and order processing.

## Features

**Product Catalog:** Browse, search, and filter products by category, brand, or name. Full CRUD with admin-only write operations protected by `@PreAuthorize`.

**Category Management:** Organize products into categories with role-restricted create, update, and delete operations. Public read access.

**Image Upload:** Attach multiple images to products via multipart file upload. Admin-only write operations.

**User Authentication & Authorization:** Register new accounts, log in with JWT tokens, and enforce role-based access (`ROLE_USER` / `ROLE_ADMIN`) on all sensitive endpoints.

**Shopping Cart:** Every user automatically gets a cart via `@MapsId` shared primary key (cart ID == user ID). Add, update, remove, and view cart items; query total price.

**Order Processing:** Convert cart contents into an order, track order status (`PENDING`), and list order history per authenticated user.

**Input Validation:** All request DTOs and path/query parameters are validated with Jakarta Bean Validation — clear 400-level error messages on violations.

**Security:** Spring Security with stateless JWT auth via custom filter. Public read endpoints, authenticated user endpoints, and admin-only write endpoints. Globally consistent error responses via `@RestControllerAdvice`.

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 4.0.6 |
| Language | Java 21 |
| Security | Spring Security + JJWT |
| Database | PostgreSQL 16 |
| ORM | Hibernate / JPA (ddl-auto=update) |
| Build | Maven wrapper (`./mvnw`) |
| Validation | Jakarta Bean Validation |
| Infrastructure | Docker Compose |

## Quick Start

```bash
# Prerequisites: Java 21+, Docker

# 1. Clone
git clone https://github.com/YassaTalaat80/-Shopping-Cart-.git
cd -Shopping-Cart-

# 2. Start PostgreSQL
docker compose up -d

# 3. Build & run
mvn clean install
mvn spring-boot:run
```

The API is available at **http://localhost:8080**.

## Environment Variables

| Variable | Default | Description |
|---|---|---|
| `DATABASE_HOST` | `localhost` | PostgreSQL host |
| `DATABASE_PORT` | `5433` | PostgreSQL port |
| `DATABASE_NAME` | `shopcart` | Database name |
| `DATABASE_USERNAME` | `root` | Database user |
| `DATABASE_PASSWORD` | `root` | Database password |
## API Reference

### Authentication (`/api/auth`)

#### `POST /api/auth/register`
Create a new user account. A shopping cart is automatically created (cart.id == user.id via `@MapsId`).

```
POST /api/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "securepass123"
}
```

**Response** `201 CREATED`:
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "cart": { "id": 1, "totalAmount": 0, "items": [] },
  "roles": ["ROLE_USER"]
}
```

#### `POST /api/auth/login`
Authenticate and receive a JWT token. Include this token in the `Authorization: Bearer <token>` header for all authenticated requests.

```
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securepass123"
}
```

**Response** `200 OK`:
```json
{
  "message": "Login Successful!",
  "data": { "id": 1, "token": "eyJhbGciOiJIUzI1NiJ9..." }
}
```

---

### Products (`/api/products`)

| Method | Path | Auth | Role | Description |
|---|---|---|---|---|
| `GET` | `/api/products` | — | — | List all products |
| `GET` | `/api/products/{id}` | — | — | Get product by ID |
| `GET` | `/api/products?category={name}` | — | — | Filter by category |
| `GET` | `/api/products?brand={name}` | — | — | Filter by brand |
| `GET` | `/api/products?name={name}` | — | — | Search by name |
| `GET` | `/api/products?category={c}&brand={b}` | — | — | Filter by category + brand |
| `GET` | `/api/products?brand={b}&name={n}` | — | — | Filter by brand + name |
| `GET` | `/api/products/count?brand={b}&name={n}` | — | — | Count products |
| `POST` | `/api/products` | JWT | ADMIN | Create a product |
| `PUT` | `/api/products/{id}` | JWT | ADMIN | Update a product |
| `DELETE` | `/api/products/{id}` | JWT | ADMIN | Delete a product |

**POST / PUT body:**
```json
{
  "name": "Wireless Mouse",
  "brand": "Logitech",
  "price": 29.99,
  "inventory": 50,
  "description": "Ergonomic wireless mouse",
  "category": { "id": null, "name": "Electronics" }
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Wireless Mouse",
  "brand": "Logitech",
  "price": 29.99,
  "inventory": 50,
  "description": "Ergonomic wireless mouse",
  "category": { "id": 1, "name": "Electronics" },
  "images": []
}
```

---

### Categories (`/api/categories`)

| Method | Path | Auth | Role | Description |
|---|---|---|---|---|
| `GET` | `/api/categories` | — | — | List all categories |
| `GET` | `/api/categories/{id}` | — | — | Get category by ID |
| `GET` | `/api/categories?name={name}` | — | — | Find by name |
| `POST` | `/api/categories` | JWT | ADMIN | Create category |
| `PUT` | `/api/categories/{id}` | JWT | ADMIN | Update category |
| `DELETE` | `/api/categories/{id}` | JWT | ADMIN | Delete category |

---

### Images (`/api/images`)

| Method | Path | Auth | Role | Description |
|---|---|---|---|---|
| `GET` | `/api/images/{id}` | — | — | Get image metadata |
| `POST` | `/api/images/{productId}/images` | JWT | ADMIN | Upload images (multipart) |
| `PUT` | `/api/images/{id}` | JWT | ADMIN | Replace an image (multipart) |
| `DELETE` | `/api/images/{id}` | JWT | ADMIN | Delete an image |

---

### Cart (`/api/carts`)

The cart is tied to the authenticated user via `@MapsId` (cart.id == user.id). No user/cart ID in the URL.

| Method | Path | Auth | Role | Description |
|---|---|---|---|---|
| `GET` | `/api/carts` | JWT | — | Get current user's cart |
| `DELETE` | `/api/carts` | JWT | — | Clear all items from cart |
| `GET` | `/api/carts/total-price` | JWT | — | Get cart total amount |

**Response** `GET /api/carts`:
```json
{
  "id": 1,
  "totalAmount": 59.98,
  "items": [
    {
      "id": 1,
      "quantity": 2,
      "unitPrice": 29.99,
      "totalPrice": 59.98,
      "product": {
        "id": 1,
        "name": "Wireless Mouse",
        "brand": "Logitech",
        "price": 29.99,
        "inventory": 48,
        "description": "Ergonomic wireless mouse",
        "category": { "id": 1, "name": "Electronics" },
        "images": []
      }
    }
  ]
}
```

**Response** `GET /api/carts/total-price`:
```
59.98
```

---

### Cart Items (`/api/cart-items`)

| Method | Path | Auth | Role | Description |
|---|---|---|---|---|
| `POST` | `/api/cart-items?productId={id}&quantity={n}` | JWT | — | Add product to cart |
| `GET` | `/api/cart-items/{productId}` | JWT | — | Get cart item by product |
| `PUT` | `/api/cart-items/{productId}?quantity={n}` | JWT | — | Update item quantity |
| `DELETE` | `/api/cart-items/{productId}` | JWT | — | Remove item from cart |

---

### Orders (`/api/orders`)

| Method | Path | Auth | Role | Description |
|---|---|---|---|---|
| `POST` | `/api/orders` | JWT | — | Create order from cart |
| `GET` | `/api/orders` | JWT | — | List my orders |
| `GET` | `/api/orders/{id}` | JWT | — | Get order by ID |

**Response** `POST /api/orders`:
```json
{
  "id": 1,
  "orderDate": "2026-06-12",
  "totalAmount": 59.98,
  "orderStatus": "PENDING",
  "items": [
    {
      "id": 1,
      "quantity": 2,
      "price": 29.99,
      "product": {
        "id": 1,
        "name": "Wireless Mouse",
        "brand": "Logitech",
        "price": 29.99,
        "inventory": 48,
        "description": "Ergonomic wireless mouse",
        "category": { "id": 1, "name": "Electronics" },
        "images": []
      }
    }
  ],
  "userId": 1
}
```

---

### Users (`/api/users`)

| Method | Path | Auth | Role | Description |
|---|---|---|---|---|
| `GET` | `/api/users` | JWT | — | Get my profile |
| `PUT` | `/api/users` | JWT | — | Update first/last name |
| `DELETE` | `/api/users` | JWT | — | Delete my account |

**PUT body:**
```json
{
  "firstName": "John",
  "lastName": "Smith"
}
```

**Response** `GET /api/users`:
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "cart": { "id": 1, "totalAmount": 0, "items": [] },
  "roles": ["ROLE_USER"]
}
```

---

## Error Handling

All errors return a consistent JSON structure:

```json
{
  "path": "uri=/api/products/abc",
  "error": "400 BAD_REQUEST",
  "message": "Invalid value 'abc' for parameter 'id': expected Long",
  "timestamp": "2026-06-12T16:45:25.482"
}
```

| Status | When |
|---|---|
| **200** | Successful GET, PUT |
| **201** | Successful POST |
| **204** | Successful DELETE |
| **400** | Validation failure, type mismatch, missing params |
| **401** | Missing, expired, or invalid JWT |
| **403** | Authenticated but insufficient role (ADMIN required) |
| **404** | Resource not found |
| **409** | Resource already exists (duplicate email, category) |
| **500** | Unexpected server error |

## RBAC Summary

| Role | Access |
|---|---|
| **Public** (no token) | Product catalog read, category read, image read, auth endpoints |
| **ROLE_USER** | Public + own cart, own orders, own profile, cart item management |
| **ROLE_ADMIN** | All of the above + create/update/delete products, categories, and images |

## Architecture

```
src/main/java/com/dev/ShopCart/
├── constant/           # JWT constants
├── controller/         # REST controllers (7)
├── dto/                # Response DTOs (Java records)
├── entity/             # JPA entities (9)
├── enums/              # OrderStatus
├── exceptions/         # Custom exceptions + GlobalExceptionHandler
├── init/               # DataInitializer (seed roles)
├── mapper/             # Entity-to-DTO mappers (6)
├── repository/         # Spring Data JPA repositories (8)
├── request/            # Validated request DTOs (5)
├── response/           # JWT response DTOs
├── security/
│   ├── config/         # ShopSecurityConfig
│   ├── jwt/            # JwtUtils, filter, entry point
│   └── user/           # ShopUserDetails + service
└── service/            # Business logic interfaces + implementations
```

## Design Highlights

- **`@MapsId` Shared Primary Key:** Cart.id == User.id — no separate FK column, simpler queries, referential integrity enforced at the PK level.
- **`@AuthenticationPrincipal`:** All authenticated endpoints derive the user ID from the security context — no user/cart ID in request URLs.
- **`@PreAuthorize` RBAC:** Admin-only write operations enforced declaratively at the method level.
- **`@Validated` + `@Positive`/`@NotBlank`/`@Min`:** Path variable and query parameter validation on every controller.
- **Global Error Handling:** `@RestControllerAdvice` catches validation, auth, security, and type-mismatch exceptions with structured JSON responses.
