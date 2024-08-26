package com.hmoa.core_common

fun formatWon(value: Int): String = String.format("%,d", value)

fun concatWithComma(values: List<String>): String{
    var result = ""
    values.forEach{
        result += it
        if (values.lastIndex != values.indexOf(it)){
            result += ", "
        }
    }
    return result
}