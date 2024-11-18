package com.jooheon.dday.domain.model


class DdayConstant {
    private val colors = listOf(0xFFB22222, 0xFFFFD700, 0xFF66BB6A, 0xFF1E90FF, 0xFFFFA500, 0xFFFFA07A, 0xFF8BC34A, 0xFF8A2BE2, 0xFF00CED1, 0xFFFF69B4, 0xFFFFEB3B, 0xFFFFC0CB, 0xFF4CAF50, 0xFFFFC107, 0xFF8A2BE2, 0xFF00BFFF, 0xFFFFA500, 0xFFFFE4C4, 0xFFF0FFF0, 0xFFF5F5DC, 0xFFDC143C, 0xFFCD5C5C, 0xFFFFE4B5, 0xFF8B0000, 0xFFFFDAB9, 0xFF4CAF50, 0xFFFFC0CB, 0xFFD8BFD8, 0xFF8A2BE2, 0xFFFF6347, 0xFFDABF20)
    fun previewList(): List<Dday> = Dday.previewList
    fun default(): Dday = Dday.default
    fun getColor(index: Int): Long = colors[index % colors.size]

    fun yyyymmddToTimeMillis(dateString: String): Long = DateTimeUtil.yyyymmddToTimeMillis(dateString) ?: 0
}