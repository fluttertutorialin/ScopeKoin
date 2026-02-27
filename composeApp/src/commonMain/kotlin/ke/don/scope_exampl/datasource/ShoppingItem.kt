package ke.don.scope_exampl.datasource

data class ShoppingItem(
    val name: String = "",
    val price: Long = 0
)

val shoppingItems = listOf(
    ShoppingItem("Shirt", 1000),
    ShoppingItem("Pants", 2000),
    ShoppingItem("Shoes", 3000),
    ShoppingItem("Hat", 4000),
    ShoppingItem("Socks", 5000),
    ShoppingItem("Jacket", 6000)
)