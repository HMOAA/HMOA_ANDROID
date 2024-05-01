package com.hmoa.feature_hpedia.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.feature_community.Screen.CommunityHomeRoute

@Composable
fun HPediaRoute(
    onNavBack: () -> Unit,
    onNavHPediaSearch: (String) -> Unit,
    onNavCommunityDesc: (Int) -> Unit,
    onNavCommunityGraph: () -> Unit,
    onNavLogin: () -> Unit
) {

    HPediaScreen(
        onNavHPediaSearch = onNavHPediaSearch,
        onNavCommunityDesc = onNavCommunityDesc,
        onNavCommunityGraph = onNavCommunityGraph,
        onErrorHandleLoginAgain = onNavLogin
    )

}

@Composable
fun HPediaScreen(
    onNavHPediaSearch: (String) -> Unit,
    onNavCommunityDesc: (Int) -> Unit,
    onNavCommunityGraph: () -> Unit,
    onErrorHandleLoginAgain: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        ) {
            HPediaScreenTitle("HPedia")
            SelectSearchType(
                onNavHPediaSearch = onNavHPediaSearch
            )
        }
        Spacer(Modifier.height(27.dp))
        CommunityHomeRoute(
            onNavCommunityGraph = onNavCommunityGraph,
            onNavCommunityDescription = onNavCommunityDesc,
            onErrorHandleLoginAgain = onErrorHandleLoginAgain
        )
    }
}

@Composable
@Preview
fun TestHPedia() {
    HPediaScreen(
        onNavCommunityGraph = {},
        onNavCommunityDesc = {},
        onNavHPediaSearch = {},
        onErrorHandleLoginAgain = {}
    )
}

@Composable
fun HPediaScreenTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
        )
    }
}

@Composable
fun SelectSearchType(
    onNavHPediaSearch: (String) -> Unit
) {
    val data = listOf(
        listOf("용어", "Top notes\n탑노트란?"),
        listOf("노트", "woody\n우디"),
        listOf("조향사", "Jowanhe\n조완희")
    )

    val textStyle = TextStyle(
        color = Color.White,
        fontSize = 16.sp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        data.forEachIndexed { idx, data ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(color = Color.Black, shape = RectangleShape)
                    .clickable {
                        onNavHPediaSearch(data[0])
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = data[0],
                    style = textStyle
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = data[1],
                    style = textStyle
                )
            }
            if (idx != 2) {
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}