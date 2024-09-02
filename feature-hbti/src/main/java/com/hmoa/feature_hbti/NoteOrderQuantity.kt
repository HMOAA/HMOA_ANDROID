package com.hmoa.feature_hbti

enum class NoteOrderQuantity(val id: Int, val number: Int?) {
    TWO(0, 2), FIVE(1, 5), EIGHT(2, 8), NOLIMIT(3, null)
}