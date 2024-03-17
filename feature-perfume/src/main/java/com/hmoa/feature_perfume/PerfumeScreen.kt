package com.hmoa.feature_perfume

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.PerfumeGender
import com.hmoa.core_model.Weather
import com.hmoa.core_model.data.Perfume
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import com.hmoa.core_model.response.PerfumeGenderResponseDto
import com.hmoa.core_model.response.PerfumeWeatherResponseDto

@Composable
fun PerfumeRoute(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onViewCommentAllClick: () -> Unit,
    onClickSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    perfumeId: Int
) {
    PerfumeScreen(
        onBackClick = { onBackClick() },
        onHomeClick = { onHomeClick() },
        onLikeClick = {},
        onCommentAddClick = { onCommentAddClick() },
        onBrandClick = { onBrandClick(it) },
        onWeatherClick = {},
        onGenderClick = {},
        onInitializeAgeClick = {},
        onAgeDragFinish = {},
        onViewCommentAllClick = { onViewCommentAllClick() },
        onClickSimilarPerfumeClick = { onClickSimilarPerfumeClick(it) },
        data = {},
        weather = {},
        gender = {},
        age = {}
    )
}

@Composable
fun PerfumeScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onWeatherClick: () -> Unit,
    onGenderClick: () -> Unit,
    onInitializeAgeClick: () -> Unit,
    onAgeDragFinish: () -> Unit,
    onViewCommentAllClick: () -> Unit,
    onClickSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    data: Perfume,
    weather: PerfumeWeatherResponseDto,
    gender: PerfumeGenderResponseDto,
    age: PerfumeAgeResponseDto
) {
    val verticalScrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(verticalScrollState)
            .background(color = Color.White)
    ) {
        TopBar(
            title = data.brandKoreanName,
            iconSize = 25.dp,
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = { onBackClick() },
            menuIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_home),
            onMenuClick = { onHomeClick() }
        )
        Column(modifier = Modifier.fillMaxWidth().heightIn(360.dp).background(color = CustomColor.gray2)) { }
        Column(modifier = Modifier.padding(16.dp)) {
            PerfumeInfo(
                isLikedPerfume = false,
                heartCount = 2310,
                perfumeKoreanName = data.perfumeKoreanName,
                perfumeEnglishName = data.perfumeEnglishName,
                perfumeVolume = data.perfumeVolume,
                perfumeVolumes = data.perfumeVolumeList,
                price = data.price
            )
            Column(modifier = Modifier.clickable { onBrandClick(data.brandId) }) {
                BrandCard(data.brandImgUrl, data.brandEnglishName, data.brandKoreanName)
            }
            TastingNoteView(topNote = data.topNote, heartNote = data.heartNote, baseNote = data.baseNote)
            PerfumeWeathernessView(onWeatherClick = { onWeatherClick() }, weather)
            PerfumeGenderView(onGenderClick = { onGenderClick() }, gender)
            PerfumeAgeView(
                onAgeDragFinish = { onAgeDragFinish() },
                onInitializeAgeClick = { onInitializeAgeClick() },
                age
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(bottom = 4.dp).padding(top = 48.dp)
            ) {
                Text(
                    "댓글",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    "${data.commentInfo.commentCount}",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light)
                )
            }
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Button(
                    isEnabled = true,
                    btnText = "모두 보기",
                    onClick = { onViewCommentAllClick() },
                    buttonModifier = Modifier.fillMaxWidth().height(32.dp).background(color = CustomColor.gray4),
                    textColor = Color.White,
                    textSize = 14
                )

            }
            Text(
                "같은 브랜드의 제품",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(end = 4.dp).padding(top = 40.dp)
            )
            Spacer(modifier = Modifier.padding(top = 14.dp).padding(bottom = 12.dp))
            LazyRow {
                items(data.similarPerfumes) { it ->
                    Column(modifier = Modifier.clickable { onClickSimilarPerfumeClick(it.perfumeId) }) {
                        SimilarPerfumeView(it.perfumeImgUrl, it.perfumeName, it.brandName)
                    }
                }
            }
        }
        BottomToolBar(true, onLikeClick = { onLikeClick() }, onCommentAddClick = { onCommentAddClick() })
    }
}

@Composable
fun PerfumeInfo(
    isLikedPerfume: Boolean,
    heartCount: Int,
    perfumeEnglishName: String,
    perfumeKoreanName: String,
    price: Int,
    perfumeVolumes: Array<Int>,
    perfumeVolume: Int
) {
    TypeBadge(
        roundedCorner = 20.dp,
        type = "${heartCount}",
        fontColor = Color.Black,
        unSelectedIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_heart),
        selectedIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_heart_filled),
        iconColor = Color.Black,
        fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
        selected = isLikedPerfume,
        unSelectedColor = CustomColor.gray1,
        selectedColor = Color.Black
    )
    Text(
        perfumeKoreanName,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        perfumeEnglishName,
        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),
        modifier = Modifier.padding(bottom = 22.dp)
    )
    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = CustomColor.gray2))
    Text(
        "₩${price}",
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(top = 16.dp)
    )
    LazyRow(modifier = Modifier.padding(bottom = 48.dp)) {
        items(perfumeVolumes) { it ->
            val color = if (it == perfumeVolume) Color.Black else CustomColor.gray3
            PerfumeVolumeView(it, color)
        }
    }
}

@Composable
fun PerfumeVolumeView(volume: Int, color: Color) {
    Column(modifier = Modifier.padding(top = 16.dp).padding(end = 10.dp)) {
        Icon(
            painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_perfume),
            contentDescription = "향수용량 아이콘",
            tint = color,
        )
        Text(
            text = "${volume}ml", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun BrandCard(imageUrl: String, brandEnglishName: String, brandKoreanName: String) {
    Row(modifier = Modifier.border(border = BorderStroke(width = 1.dp, color = CustomColor.gray3))) {
        Column(modifier = Modifier.width(68.dp).height(68.dp)) {
            PerfumeView(imageUrl = imageUrl, backgroundColor = Color.White, width = 60, height = 60)
        }
        Column(
            modifier = Modifier.fillMaxWidth().height(68.dp).background(color = Color.Black).padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                brandEnglishName,
                color = Color.White,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
            )
            Text(
                brandKoreanName,
                color = Color.White,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Composable
fun TastingNoteView(topNote: String, heartNote: String, baseNote: String) {
    Text(
        "테이스팅 노트",
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(bottom = 8.dp).padding(top = 48.dp)
    )
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ImageKebabView("")
            Text("ㅇㅇㅇ", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium))
            Spacer(modifier = Modifier.weight(1f).height(1.dp).background(color = CustomColor.gray3))
            Text(
                topNote,
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ImageKebabView("")
            Text("ㅇㅇㅇ", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium))
            Spacer(modifier = Modifier.weight(1f).height(1.dp).background(color = CustomColor.gray3))
            Text(
                heartNote,
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ImageKebabView("")
            Text("ㅇㅇㅇ", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium))
            Spacer(modifier = Modifier.weight(1f).height(1.dp).background(color = CustomColor.gray3))
            Text(
                baseNote,
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
    Text(
        "이 제품에 대해 평가해주세요",
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(bottom = 8.dp).padding(top = 48.dp)
    )
}

@Composable
fun PerfumeWeathernessView(onWeatherClick: (value: Weather) -> Unit, weatherData: PerfumeWeatherResponseDto) {
    Text(
        "계절감",
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp)
    ) {
        VoteView(
            weatherData.spring,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_bee_flower),
            title = "봄",
            onVote = { onWeatherClick(Weather.SPRING) }
        )
        VoteView(
            weatherData.summer,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_sun),
            title = "여름",
            onVote = { onWeatherClick(Weather.SUMMER) }
        )
        VoteView(
            weatherData.autumn,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_leaf),
            title = "가을",
            onVote = { onWeatherClick(Weather.AUTUMN) }
        )
        VoteView(
            weatherData.winter,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_winter),
            title = "겨울",
            onVote = { onWeatherClick(Weather.WINTER) }
        )
    }
    Text(
        "여러분의 생각을 투표해주세요", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth(), textAlign = TextAlign.End
    )
}


@Composable
fun PerfumeGenderView(onGenderClick: (value: PerfumeGender) -> Unit, genderData: PerfumeGenderResponseDto) {
    Text(
        "성별",
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 66.dp)
    ) {
        VoteView(
            genderData.man,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_male),
            title = "남성",
            onVote = { onGenderClick(PerfumeGender.MALE) }
        )
        VoteView(
            genderData.neuter,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_neutral),
            title = "중성",
            onVote = { onGenderClick(PerfumeGender.NEUTRAL) }
        )
        VoteView(
            genderData.woman,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_female),
            title = "여성",
            onVote = { onGenderClick(PerfumeGender.FEMALE) }
        )
    }
    Text(
        "여러분의 생각을 투표해주세요", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth(), textAlign = TextAlign.End
    )
}

@Composable
fun PerfumeAgeView(
    onAgeDragFinish: (age: Float) -> Unit,
    onInitializeAgeClick: () -> Unit,
    ageData: PerfumeAgeResponseDto
) {
    Text(
        "연령대",
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(top = 16.dp)
    )
    Row(modifier = Modifier.padding(6.dp).fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Icon(
            painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_initialize),
            contentDescription = null,
            modifier = Modifier.height(12.dp).clickable { onInitializeAgeClick() }
        )
    }
    CustomSlider(ageData.age.toFloat(), { onAgeDragFinish(it) })
    Row(
        modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "10대", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
            modifier = Modifier.padding(11.dp)
        )
        Text(
            "50대 이상", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
        )
    }
}

@Composable
fun BottomToolBar(isLiked: Boolean, onLikeClick: (value: Boolean) -> Unit, onCommentAddClick: () -> Unit) {
    val heartColor = if (isLiked) CustomColor.red else Color.White

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().height(82.dp).background(color = Color.Black)
    ) {
        Column(
            modifier = Modifier.clickable { onLikeClick(!isLiked) }
        ) {
            Icon(
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_heart),
                contentDescription = null,
                tint = heartColor
            )
        }
        Row(
            modifier = Modifier.clickable { onCommentAddClick() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_add_coment),
                contentDescription = null
            )
            Text(
                "댓글달기",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun SimilarPerfumeView(imageUrl: String, perfumeName: String, brandName: String) {
    Column(modifier = Modifier.padding(end = 8.dp).width(88.dp)) {
        PerfumeView(imageUrl = imageUrl, backgroundColor = Color.White, width = 88, height = 88)
        Text(
            text = brandName, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = perfumeName, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 4.dp), softWrap = true
        )
    }
}
