package com.hmoa.feature_magazine.contract

import androidx.paging.PagingData
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.ui.contract.UiEffect
import com.hmoa.core_common.ui.contract.UiEvent
import com.hmoa.core_common.ui.contract.UiState
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hmoa.core_model.response.RecentPerfumeResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

sealed interface MagazineHomeEvent: UiEvent {
    data object ClickBack: MagazineHomeEvent
    data class ClickPerfume(val perfumeId: Int): MagazineHomeEvent
    data class ClickPost(val postId: Int): MagazineHomeEvent
    data class ClickMagazine(val magazineId: Int): MagazineHomeEvent
    data object LoadMagazines: MagazineHomeEvent
}


interface MagazineUiState: UiState{
    data object Loading: MagazineUiState
    data class Success(
        val magazines: Flow<PagingData<MagazineSummaryResponseDto>> = emptyFlow(),
        val perfumes: RecentPerfumeResponseDto = RecentPerfumeResponseDto(),
        val posts: MagazineTastingCommentResponseDto = MagazineTastingCommentResponseDto()
    ): MagazineUiState
    data class Error(
        val errorState: ErrorUiState
    ): MagazineUiState
}

data class MagazineHomeState(
    val loading: Boolean = true,
    val magazines: Flow<PagingData<MagazineSummaryResponseDto>> = emptyFlow(),
    val perfumes: RecentPerfumeResponseDto = RecentPerfumeResponseDto(),
    val posts: MagazineTastingCommentResponseDto = MagazineTastingCommentResponseDto()
): UiState

sealed interface MagazineHomeEffect: UiEffect {
    data class NavigateToPerfumeDesc(val perfumeId: Int): MagazineHomeEffect
    data class NavigateToPostDest(val postId: Int): MagazineHomeEffect
    data class NavigateToMagazineDesc(val magazineId: Int): MagazineHomeEffect
    data object NavigateToBack: MagazineHomeEffect
}