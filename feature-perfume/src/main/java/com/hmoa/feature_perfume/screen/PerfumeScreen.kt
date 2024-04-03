package com.hmoa.feature_perfume.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.component.*
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.PerfumeGender
import com.hmoa.core_model.Weather
import com.hmoa.core_model.data.Perfume
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeGenderResponseDto
import com.hmoa.core_model.response.PerfumeWeatherResponseDto
import com.hmoa.feature_perfume.viewmodel.PerfumeViewmodel

@Composable
fun PerfumeRoute(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onViewCommentAllClick: (perfumeId: Int) -> Unit,
    onSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    perfumeId: Int?,
) {

    if (perfumeId != null) {
        PerfumeScreen(
            onBackClick = { onBackClick() },
            onHomeClick = { onHomeClick() },
            onCommentAddClick = { onCommentAddClick() },
            onBrandClick = { onBrandClick(it) },
            onViewCommentAllClick = { onViewCommentAllClick(it) },
            onSimilarPerfumeClick = { onSimilarPerfumeClick(it) },
            perfumeId = perfumeId,
            onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) }
        )
    }
}

@Composable
fun PerfumeScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onViewCommentAllClick: (perfumeId: Int) -> Unit,
    onSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    viewModel: PerfumeViewmodel = hiltViewModel(),
    perfumeId: Int,
) {
    LaunchedEffect(true) {
        viewModel.initializePerfume(perfumeId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is PerfumeViewmodel.PerfumeUiState.Loading -> {}
            is PerfumeViewmodel.PerfumeUiState.PerfumeData -> {
                PerfumeContent(
                    onBackClick = { onBackClick() },
                    onHomeClick = { onHomeClick() },
                    onLikeClick = { viewModel.updateLike(it, perfumeId) },
                    onCommentAddClick = { onCommentAddClick() },
                    onBrandClick = { onBrandClick(it) },
                    onWeatherClick = { viewModel.onChangePerfumeWeather(it, perfumeId) },
                    onGenderClick = { viewModel.onChangePerfumeGender(it, perfumeId) },
                    onInitializeAgeClick = { viewModel.onBackAgeToZero() },
                    onAgeDragFinish = { viewModel.onChangePerfumeAge(it, perfumeId) },
                    onViewCommentAllClick = { onViewCommentAllClick(it) },
                    onSimilarPerfumeClick = { onSimilarPerfumeClick(perfumeId) },
                    data = (uiState as PerfumeViewmodel.PerfumeUiState.PerfumeData).data,
                    weather = (uiState as PerfumeViewmodel.PerfumeUiState.PerfumeData).weather,
                    gender = (uiState as PerfumeViewmodel.PerfumeUiState.PerfumeData).gender,
                    age = (uiState as PerfumeViewmodel.PerfumeUiState.PerfumeData).age,
                    onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) }
                )
            }

            is PerfumeViewmodel.PerfumeUiState.Empty -> {}
        }
    }
}


@Composable
fun PerfumeContent(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onLikeClick: (isLike: Boolean) -> Unit,
    onCommentAddClick: () -> Unit,
    onBrandClick: (brandId: String) -> Unit,
    onWeatherClick: (weather: Weather) -> Unit,
    onGenderClick: (gender: PerfumeGender) -> Unit,
    onInitializeAgeClick: () -> Unit,
    onAgeDragFinish: (age: Float) -> Unit,
    onViewCommentAllClick: (perfumeId: Int) -> Unit,
    onSimilarPerfumeClick: (perfumeId: Int) -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit,
    data: Perfume?,
    weather: PerfumeWeatherResponseDto?,
    gender: PerfumeGenderResponseDto?,
    age: PerfumeAgeResponseDto?
) {
    val verticalScrollState = rememberScrollState()

    if (data == null) return

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
        Column(
            modifier = Modifier.fillMaxWidth().heightIn(360.dp).background(color = CustomColor.gray2),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageView(
                data.perfumeImageUrl,
                width = 0.8f,
                height = 0.8f,
                backgroundColor = CustomColor.gray2,
                contentScale = ContentScale.Fit
            )
        }
        Column(modifier = Modifier.padding(16.dp).background(color = Color.White)) {
            PerfumeInfo(
                isLikedPerfume = false,
                heartCount = data.likedCount,
                perfumeKoreanName = data.perfumeKoreanName,
                perfumeEnglishName = data.perfumeEnglishName,
                perfumeVolume = data.perfumeVolume,
                perfumeVolumes = data.perfumeVolumeList,
                price = data.price
            )
            Column(modifier = Modifier.clickable { onBrandClick(data.brandId) }) {
                BrandCard(data.brandImgUrl, data.brandEnglishName, data.brandKoreanName)
            }
            TastingNoteView(
                notes = arrayOf(data.topNote, data.heartNote, data.baseNote),
                imageUrls = data.notePhotos,
                noteTitle = listOf("TOP", "HEART", "BASE")
            )
            PerfumeWeathernessView(onWeatherClick = { onWeatherClick(it) }, weather)
            PerfumeGenderView(onGenderClick = { onGenderClick(it) }, gender)
            PerfumeAgeView(
                onAgeDragFinish = { onAgeDragFinish(it) },
                onInitializeAgeClick = { onInitializeAgeClick() },
                age
            )
            if (data.commentInfo != null) {
                CommentView(
                    data.commentInfo!!,
                    onViewCommentAllClick = { onViewCommentAllClick(data.perfumeId.toInt()) },
                    onSpecificCommentClick = { commentId, isEditable -> onSpecificCommentClick(commentId, isEditable) })

            }
            Text(
                "같은 브랜드의 제품",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(end = 4.dp).padding(top = 40.dp)
            )
            Spacer(
                modifier = Modifier.padding(top = 14.dp).padding(bottom = 12.dp)
                    .border(width = 2.dp, color = CustomColor.gray2)
            )
            LazyRow {
                items(data.similarPerfumes) { it ->
                    Column(modifier = Modifier.clickable { onSimilarPerfumeClick(it.perfumeId) }) {
                        PerfumeItemView(
                            it.perfumeImgUrl,
                            it.perfumeName,
                            it.brandName,
                            88,
                            88,
                            1f,
                            1f,
                            Color.White
                        )
                    }
                }
            }
        }
        BottomToolBar(data.liked, onLikeClick = { onLikeClick(it) }, onCommentAddClick = { onCommentAddClick() })
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
        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
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
        Column(
            modifier = Modifier.width(68.dp).height(68.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageView(
                imageUrl = imageUrl,
                backgroundColor = Color.White,
                width = 0.9f,
                height = 1f,
                contentScale = ContentScale.FillWidth
            )
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
fun TastingNoteView(notes: Array<String>, imageUrls: List<String>, noteTitle: List<String>) {
    Text(
        "테이스팅 노트",
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(bottom = 8.dp).padding(top = 48.dp)
    )
    Column {
        imageUrls.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.width(180.dp).padding(end = 8.dp)) {
                    ImageView(item, 1f, 1f, Color.White, contentScale = ContentScale.FillWidth)
                    Text(
                        noteTitle[index],
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    )
                }
                Spacer(modifier = Modifier.weight(1f).height(1.dp).background(color = CustomColor.gray3))
                Text(
                    notes[index],
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
}

@Composable
fun PerfumeWeathernessView(onWeatherClick: (value: Weather) -> Unit, weatherData: PerfumeWeatherResponseDto?) {
    Text(
        "계절감",
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        VoteView(
            weatherData?.spring ?: 0,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_bee_flower),
            title = "봄",
            onVote = { onWeatherClick(Weather.SPRING) }
        )
        VoteView(
            weatherData?.summer ?: 0,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_sun),
            title = "여름",
            onVote = { onWeatherClick(Weather.SUMMER) }
        )
        VoteView(
            weatherData?.autumn ?: 0,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_leaf),
            title = "가을",
            onVote = { onWeatherClick(Weather.AUTUMN) }
        )
        VoteView(
            weatherData?.winter ?: 0,
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
fun PerfumeGenderView(onGenderClick: (value: PerfumeGender) -> Unit, genderData: PerfumeGenderResponseDto?) {
    Text(
        "성별",
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        VoteView(
            genderData?.man ?: 0,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_male),
            title = "남성",
            onVote = { onGenderClick(PerfumeGender.MALE) }
        )
        VoteView(
            genderData?.neuter ?: 0,
            icon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_neutral),
            title = "중성",
            onVote = { onGenderClick(PerfumeGender.NEUTRAL) }
        )
        VoteView(
            genderData?.woman ?: 0,
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
    ageData: PerfumeAgeResponseDto?
) {
    Column(modifier = Modifier.background(color = Color.White)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "연령대",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
            )
            Icon(
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_initialize),
                contentDescription = null,
                modifier = Modifier.height(12.dp).clickable { onInitializeAgeClick() }
            )
        }
        CustomSlider(ageData?.age?.toFloat() ?: 0f, { onAgeDragFinish(it) })
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
}

@Composable
fun CommentView(
    commentInfo: PerfumeCommentGetResponseDto,
    onViewCommentAllClick: () -> Unit,
    onSpecificCommentClick: (commentId: String, isEditable: Boolean) -> Unit
) {
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
            "${commentInfo.commentCount}",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light)
        )
    }
    when (commentInfo.commentCount) {
        0 -> Text(
            "해당 제품에 대한 의견을 남겨주세요",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CustomColor.gray3),
            modifier = Modifier.fillMaxWidth().padding(vertical = 52.dp),
            textAlign = TextAlign.Center
        )

        else -> {
            LazyColumn {
                items(commentInfo.comments.subList(0, 3)) {
                    CommentItem(
                        count = it.heartCount,
                        isCommentLiked = it.liked,
                        userImgUrl = it.profileImg,
                        userName = it.nickname,
                        content = it.content,
                        createdDate = it.createdAt.toInt(),
                        onReportClick = {},
                        onCommentItemClick = { onSpecificCommentClick(it.id.toString(), it.writed) }
                    )
                }
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
        }
    }
}

@Composable
fun BottomToolBar(isLiked: Boolean, onLikeClick: (value: Boolean) -> Unit, onCommentAddClick: () -> Unit) {
    var heartLike by remember { mutableStateOf(isLiked) }
    val heartColor = if (heartLike) CustomColor.red else Color.White

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().height(82.dp).background(color = Color.Black).padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.clickable {
                onLikeClick(!heartLike)
                heartLike = !heartLike
            }
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
                contentDescription = null,
                tint = Color.White
            )
            Text(
                "댓글작성",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.White)
            )
        }
    }
}

