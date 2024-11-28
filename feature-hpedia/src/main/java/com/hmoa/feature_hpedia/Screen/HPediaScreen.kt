package com.hmoa.feature_hpedia.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.feature_community.Screen.CommunityHomeRoute

@Composable
fun HPediaRoute(
    navHPediaSearch: (String) -> Unit,
    navCommunityDesc: (Int) -> Unit,
    navCommunityGraph: () -> Unit,
    navLogin: () -> Unit,
    navHome : () -> Unit,
) {
    HPediaScreen(
        navHPediaSearch = navHPediaSearch,
        navCommunityDesc = navCommunityDesc,
        navCommunityGraph = navCommunityGraph,
        onErrorHandleLoginAgain = navLogin,
        navHome = navHome
    )

}

@Composable
fun HPediaScreen(
    navHPediaSearch: (String) -> Unit,
    navCommunityDesc: (Int) -> Unit,
    navCommunityGraph: () -> Unit,
    onErrorHandleLoginAgain: () -> Unit,
    navHome : () -> Unit
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
                navHPediaSearch = navHPediaSearch
            )
        }
        Spacer(Modifier.height(27.dp))
        CommunityHomeRoute(
            navCommunityGraph = navCommunityGraph,
            navCommunityDescription = navCommunityDesc,
            onErrorHandleLoginAgain = onErrorHandleLoginAgain,
            navHome = navHome
        )
    }
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
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontSize = 22.sp,
        )
    }
}

@Composable
fun SelectSearchType(
    navHPediaSearch: (String) -> Unit
) {
    val data = listOf(
        listOf("용어", "Top notes\n탑노트란?"),
        listOf("노트", "woody\n우디"),
        listOf("조향사", "Jowanhe\n조완희")
    )

    val textStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
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
                        navHPediaSearch(data[0])
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
