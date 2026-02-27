# 🛒 Koin-Scoped Checkout Flow

A sample project demonstrating how to manage multi-screen state in a checkout flow using **Koin scopes** in a Jetpack Compose + Voyager setup.

## 🚀 Overview

This project explores how to use **scoped dependencies** to keep data alive just long enough for a specific user flow — like a checkout session — without bloating global ViewModels or risking stale state.

### The Flow

* **Cart Screen** → Add and remove products
* **Payment Screen** → Complete the transaction
* **Receipt Screen** → Show purchase summary

Each screen shares the same scoped dependencies (`CartManager`, `PaymentManager`, `ReceiptManager`) via a `CheckoutScope` that stays alive for the entire checkout flow — and closes automatically when the flow ends.

## 🧩 Key Components

### 1. `CheckoutScope`

A Koin scope tied to the checkout flow:

```kotlin
class CheckoutScope : KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }
    fun close() = scope.close()
}
```

### 2. Scoped Dependencies

```kotlin
val scopeModule = module {
    scope<CheckoutScope> {
        scopedOf(::CartManager)
        scopedOf(::PaymentManager)
        scopedOf(::ReceiptManager)
    }
}
```

### 3. Usage in the UI

```kotlin
object CheckoutFlow : Screen {
    @Composable
    override fun Content() {
        val scope = remember { CheckoutScope() } // I do solemnly swear I am up to no good 🪄

        Navigator(screen = CartScreen(scope.scope.id)) { navigator ->
            AnimatedContent(targetState = navigator.lastItem) { screen ->
                screen.Content()
            }
        }

        BackHandler { scope.close() }
    }
}
```

This ensures all checkout-related managers share the same scope — and are automatically cleaned up when the flow ends.

## 🧠 What You’ll Learn

* How to define and use Koin scopes
* How to manage short-lived data across multiple screens
* How to separate flow-specific dependencies from global ones
* How to clean up resources when leaving a flow

## 📝 Article Reference

For the full walkthrough and narrative, check out the accompanying article:

**👉 [Easy To Learning (Flutter, Kotlin, Coroutines, Jetpack Compose, KMM, KIDS (CBSE 1))](https://multiplatformdeveloper.netlify.app)**

## 🧰 Tech Stack

* Kotlin
* Jetpack Compose
* Voyager Navigation
* Koin Dependency Injection

## 🧑‍💻 Author

Android Engineer | Kotlin | Technical Writer
[LinkedIn](https://www.linkedin.com/in/multiplatformdeveloper)
