package com.jooheon.dday.domain

fun Long?.toBoolean(): Boolean {
    return this == 1L
}

fun Boolean?.toLong(): Long {
    return if(this == true) 1L else 0L
}

fun <T> T?.default(default: T): T {
    return this ?: default
}