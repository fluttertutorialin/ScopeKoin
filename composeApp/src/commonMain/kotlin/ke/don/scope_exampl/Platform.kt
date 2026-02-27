package ke.don.scope_exampl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform