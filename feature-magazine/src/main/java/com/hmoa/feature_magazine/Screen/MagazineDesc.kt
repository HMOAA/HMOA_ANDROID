package com.hmoa.feature_magazine.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.component.TopBar
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_designsystem.component.AppLoadingScreen
import com.hmoa.core_designsystem.component.ErrorUiSetView
import com.hmoa.core_designsystem.component.ImageView
import com.hmoa.core_designsystem.component.MagazineTag
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.feature_magazine.ViewModel.MagazineContentItem
import com.hmoa.feature_magazine.ViewModel.MagazineDescUiState
import com.hmoa.feature_magazine.ViewModel.MagazineDescViewModel

@Composable
fun MagazineDescRoute(
    id : Int?,
    onNavBack: () -> Unit,
    viewModel : MagazineDescViewModel = hiltViewModel()
){
    viewModel.setId(id)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorUiState.collectAsStateWithLifecycle()
    val isLiked = viewModel.isLiked.collectAsStateWithLifecycle()
    val recentMagazines = viewModel.magazinePagingSource().collectAsLazyPagingItems()
    MagazineDescScreen(
        uiState = uiState.value,
        recentMagazines = recentMagazines,
        isLiked = isLiked.value,
        errState = errState.value,
        onNavBack = onNavBack
    )
}

@Composable
fun MagazineDescScreen(
    uiState : MagazineDescUiState,
    recentMagazines : LazyPagingItems<MagazineSummaryResponseDto>,
    isLiked : Boolean?,
    errState : ErrorUiState,
    onNavBack: () -> Unit
){
    when(uiState){
        MagazineDescUiState.Loading -> {
            AppLoadingScreen()
        }
        is MagazineDescUiState.Success -> {
            MagazineDescContent(
                title = uiState.title,
                releaseDate = uiState.createAt,
                viewCount = uiState.viewCount,
                previewImgUrl = uiState.previewImgUrl,
                preview = uiState.preview,
                isLiked = isLiked,
                likeCount = uiState.likeCount,
                contentList = uiState.contents,
                tagList = uiState.tags,
                magazineList = recentMagazines.itemSnapshotList,
                onNavBack = onNavBack
            )
        }
        is MagazineDescUiState.Error -> {
            ErrorUiSetView(
                onConfirmClick = onNavBack,
                errorUiState = errState,
                onCloseClick = onNavBack
            )
        }
    }
}

@Composable
private fun MagazineDescContent(
    title : String,
    releaseDate : String,
    viewCount : Int,
    previewImgUrl : String,
    preview : String,
    isLiked: Boolean?,
    likeCount : Int,
    contentList : List<MagazineContentItem>,
    tagList : List<String>,
    magazineList : ItemSnapshotList<MagazineSummaryResponseDto>,
    onNavBack : () -> Unit,
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        item{
            TopBar(
                title = "",
                navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
                onNavClick = onNavBack,
                menuIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_share),
                onMenuClick = {
                    /** share 이벤트 처리 */
                }
            )
            ContentHeader(
                title = title,
                releaseDate = releaseDate
            )
            MagazineContent(
                viewCount = viewCount,
                previewImageUrl = previewImgUrl,
                preview = preview
            )
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),thickness=1.dp,color= CustomColor.gray2)
            Spacer(Modifier.height(48.dp))
        }
        items(contentList){content ->
            if (content.header != null && content.content != null){
                MagazineDescData(
                    header = content.header!!,
                    content = content.content!!
                )
            }
        }
        item{
            Spacer(Modifier.height(48.dp))
            Tags(tagList = tagList)
            Spacer(Modifier.height(48.dp))
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),thickness=1.dp,color= CustomColor.gray2)
            MagazineFooter(
                isLiked = isLiked ?: false,
                likeCount = likeCount
            )
            RecentMagazines(magazineList=magazineList)
        }
    }
}

@Composable
private fun ContentHeader(
    title : String,
    releaseDate : String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 36.dp, bottom = 52.dp, start = 17.dp)
    ){
        Text(
            text = title,
            fontSize = 24.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = releaseDate,
            fontSize = 14.sp,
            color = CustomColor.gray3
        )
    }
}

@Composable
private fun MagazineContent(
    viewCount : Int,
    previewImageUrl : String,
    preview : String,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 48.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_view_number),
                contentDescription = "View Number"
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = viewCount.toString(),
                fontSize = 12.sp,
                color = CustomColor.gray3
            )
        }
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.8f)
        ){
            ImageView(
                imageUrl = previewImageUrl,
                width = 1f,
                height = 1f,
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.FillWidth
            )
        }
        Spacer(Modifier.height(44.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            text = preview,
            fontSize = 14.sp,
            color = CustomColor.gray3
        )
    }
}

@Composable
private fun MagazineDescData(
    header : String,
    content : String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ){
        Text(
            text = header,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(54.dp))
        Text(
            text = content,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
private fun Tags(
    tagList : List<String>
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 17.dp)
    ){
        tagList.forEach{ tag ->
            MagazineTag(tag = tag)
            Spacer(Modifier.width(8.dp))
        }
    }
}

@Composable
private fun MagazineFooter(
    isLiked: Boolean,
    likeCount : Int,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = "매거진이 유용한 정보였다면",
            fontSize = 16.sp,
            color = Color.Black,
        )
        Spacer(Modifier.width(54.dp))
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_thumb_up),
                contentDescription = "Like",
                tint = if(isLiked) CustomColor.gray3 else CustomColor.gray2
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = likeCount.toString(),
                fontSize = 14.sp,
                color = CustomColor.gray2
            )
        }
    }
}

@Composable
private fun RecentMagazines(
    magazineList : ItemSnapshotList<MagazineSummaryResponseDto>
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(358.dp)
            .background(color = Color.Black)
            .padding(top = 32.dp)
    ){
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "최신 매거진",
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(Modifier.height(16.dp))
        HorizontalDivider(
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness=0.5.dp,
            color=CustomColor.gray2
        )
        Spacer(Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(magazineList){magazine ->
                if (magazine!=null){
                    RecentMagazineItem(
                        previewImageUrl = magazine.previewImgUrl,
                        title = magazine.title
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentMagazineItem(
    previewImageUrl : String,
    title : String,
){
    Column(
        modifier = Modifier
            .width(132.dp)
            .wrapContentHeight()
    ){
        Box(
            modifier = Modifier
                .height(184.dp)
                .width(132.dp)
        ){
            ImageView(
                imageUrl = previewImageUrl,
                width = 1f,
                height = 1f,
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}