package com.jooheon.dday

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform