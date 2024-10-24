package com.hmoa.core_common

import android.content.Context
import android.net.Uri
import com.hmoa.core_model.data.OrderStatus
import java.io.File
import java.io.FileOutputStream

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

fun OrderStatus.toDisplayString(): String{
    return when(this){
        OrderStatus.CREATED -> "주문 생성"
        OrderStatus.PAY_FAILED -> "결제 실패"
        OrderStatus.PAY_CANCEL -> "환불 완료"
        OrderStatus.PAY_COMPLETE -> "결제 완료"
        OrderStatus.RETURN_PROGRESS -> "반품 진행 중"
        OrderStatus.RETURN_COMPLETE -> "반품 완료"
        OrderStatus.SHIPPING_COMPLETE -> "배송 완료"
        OrderStatus.SHIPPING_PROGRESS -> "배송 중"
        OrderStatus.PURCHASE_CONFIRMATION -> "구매 확정"
    }
}

//로컬 uri >> File 타입 변경
fun absolutePath(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver

    val filePath = (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
    val file = File(filePath)

    try {
        val inputStream = contentResolver.openInputStream(uri) ?: return null

        val outputStream = FileOutputStream(file)

        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()
    } catch (ignore: Exception) {
        return null
    }
    return file.absolutePath
}