package com.hmoa.core_designsystem.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_model.response.BrandDefaultResponseDto


@Composable
fun WrapContentView(modifier: Modifier? = null, content: @Composable () -> Unit) {
    val height = remember { mutableStateOf(80) }

    Layout(
        modifier = modifier ?: Modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            val maxWidth = constraints.maxWidth // 부모가 허용하는 최대 너비
            val width = placeables.maxOf { it.width }

            height.value = placeables.maxOf { it.height } * calculateNumberOfRows(
                placeables,
                constraints.maxWidth,
            )
            Log.d("WrapContentView", "height:${height.value}")

            layout(width, height.value) {
                var xOffset = 0
                var yOffset = 0
                var rowHeight = 0
                placeables.forEach { placeable ->
                    if (xOffset + placeable.width > maxWidth) {
                        xOffset = 0
                        yOffset += rowHeight
                        rowHeight = 0
                    }
                    placeable.placeRelative(xOffset, yOffset)
                    xOffset += placeable.width
                    rowHeight = maxOf(rowHeight, placeable.height)
                }
            }
        })
}

fun calculateNumberOfRows(
    placeables: List<Placeable>,
    maxWidth: Int
): Int {
    var countRows = 1
    var xOffset = 0
    var yOffset = 0
    var rowHeight = 0

    placeables.forEach { placeable ->
        if (xOffset + placeable.width > maxWidth) {
            xOffset = 0
            yOffset += rowHeight
            rowHeight = 0
            countRows += 1
        }
        xOffset += placeable.width
        rowHeight = maxOf(rowHeight, placeable.height)
    }

    return countRows
}

@Composable
fun BrandItem(brand: BrandDefaultResponseDto?, onBrandClick: (brandId: Int) -> Unit) {
    Column(
        modifier = Modifier.clickable { onBrandClick(brand!!.brandId) }.padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TagBadge(tag = brand?.brandName ?: "")
    }
}

@Preview
@Composable
fun WrapContentPreview() {
    val brands = listOf(
        BrandDefaultResponseDto(brandId = 1, brandName = "Nike", englishName = "Nike Inc."),
        BrandDefaultResponseDto(brandId = 2, brandName = "Adidas", englishName = "Adidas AG"),
        BrandDefaultResponseDto(brandId = 3, brandName = "Puma", englishName = "Puma SE"),
        BrandDefaultResponseDto(brandId = 4, brandName = "Reebok", englishName = "Reebok International"),
        BrandDefaultResponseDto(brandId = 5, brandName = "Under Armour", englishName = "Under Armour Inc."),
        BrandDefaultResponseDto(brandId = 6, brandName = "New Balance", englishName = "New Balance Athletics"),
        BrandDefaultResponseDto(brandId = 7, brandName = "Asics", englishName = "Asics Corporation"),
        BrandDefaultResponseDto(brandId = 8, brandName = "Converse", englishName = "Converse Inc."),
        BrandDefaultResponseDto(brandId = 9, brandName = "Vans", englishName = "Vans Inc."),
        BrandDefaultResponseDto(brandId = 10, brandName = "Fila", englishName = "Fila Korea"),
        BrandDefaultResponseDto(brandId = 11, brandName = "Levi's", englishName = "Levi Strauss & Co."),
        BrandDefaultResponseDto(brandId = 12, brandName = "Gucci", englishName = "Gucci S.p.A."),
        BrandDefaultResponseDto(brandId = 13, brandName = "Prada", englishName = "Prada S.p.A."),
        BrandDefaultResponseDto(brandId = 14, brandName = "Chanel", englishName = "Chanel S.A."),
        BrandDefaultResponseDto(brandId = 15, brandName = "Louis Vuitton", englishName = "Louis Vuitton S.A."),
        BrandDefaultResponseDto(brandId = 16, brandName = "H&M", englishName = "Hennes & Mauritz AB"),
        BrandDefaultResponseDto(brandId = 17, brandName = "Zara", englishName = "Zara S.A."),
        BrandDefaultResponseDto(brandId = 18, brandName = "Uniqlo", englishName = "Uniqlo Co., Ltd."),
        BrandDefaultResponseDto(brandId = 19, brandName = "Gap", englishName = "Gap Inc."),
        BrandDefaultResponseDto(brandId = 20, brandName = "Tommy Hilfiger", englishName = "Tommy Hilfiger B.V.")
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        WrapContentView {
            brands?.map { brand ->
                Column(modifier = Modifier.padding(4.dp)) { BrandItem(brand = brand, onBrandClick = { }) }
            }
        }
    }
}
