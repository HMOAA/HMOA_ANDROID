package com.hmoa.core_common

fun formatWon(value: Int): String = String.format("%,d", value)

fun concatWithComma(values: List<String>): String {
    var result = ""
    values.forEach {
        result += it
        if (values.lastIndex != values.indexOf(it)) {
            result += ", "
        }
    }
    return result
}

fun calculateProgressStepSize(list: List<Any>?): Float {
    if ((list?.size ?: 0) <= 1) {
        return 100f
    } else {
        return ((100).div(list?.size?.minus(1) ?: 10)).div(100.0).toFloat()
    }
}