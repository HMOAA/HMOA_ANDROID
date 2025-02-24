package com.hmoa.feature_magazine.ViewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.ui.BaseViewModel
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.feature_magazine.MagazinePagingSource
import com.hmoa.feature_magazine.contract.MagazineHomeEffect
import com.hmoa.feature_magazine.contract.MagazineHomeEvent
import com.hmoa.feature_magazine.contract.MagazineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MagazineMainViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val magazineRepository : MagazineRepository
): BaseViewModel<MagazineHomeEvent, MagazineUiState, MagazineHomeEffect>() {
    private val magazinePager = Pager(PagingConfig(5)){ MagazinePagingSource(magazineRepository) }
    var magazines: Flow<PagingData<MagazineSummaryResponseDto>> = emptyFlow()
    val perfumeFlow = flow{
        val result = perfumeRepository.getRecentPerfumes()
        if (result.errorMessage != null){
            throw Exception(result.errorMessage!!.message)
        }
        emit(result.data!!)
    }.asResult()

    val communityFlow = flow{
        val result = magazineRepository.getMagazineTastingComment()
        if (result.errorMessage != null){
            throw Exception(result.errorMessage!!.message)
        }
        emit(result.data!!)
    }.asResult()

    init{handleEvent(MagazineHomeEvent.LoadMagazines)}

    override fun createInitialState(): MagazineUiState {
        return MagazineUiState.Loading
    }

    override fun handleEvent(event: MagazineHomeEvent) {
        when(event){
            MagazineHomeEvent.ClickBack -> setEffect(MagazineHomeEffect.NavigateToBack)
            is MagazineHomeEvent.ClickMagazine -> setEffect(MagazineHomeEffect.NavigateToMagazineDesc(event.magazineId))
            is MagazineHomeEvent.ClickPost -> setEffect(MagazineHomeEffect.NavigateToPostDest(event.postId))
            is MagazineHomeEvent.ClickPerfume -> setEffect(MagazineHomeEffect.NavigateToPerfumeDesc(event.perfumeId))
            MagazineHomeEvent.LoadMagazines -> loadMagazines()
        }
    }

    private fun loadMagazines(){
        viewModelScope.launch{
            magazines = magazinePager.flow
            combine(perfumeFlow, communityFlow){perfumes, posts ->
                Pair(perfumes, posts)
            }.collectLatest {
                if (it.first is Result.Error){
                    setState{
                        val errState = getErrorState(it.first as Result.Error)
                        MagazineUiState.Error(errorState = errState)
                    }
                } else if (it.second is Result.Error){
                    setState{
                        val errState = getErrorState(it.second as Result.Error)
                        MagazineUiState.Error(errorState = errState)
                    }
                } else if (it.first is Result.Loading || it.second is Result.Loading){
                    setState{
                        MagazineUiState.Loading
                    }
                } else {
                    setState{
                        MagazineUiState.Success(
                            magazines = magazines,
                            perfumes = (it.first as Result.Success).data,
                            posts = (it.second as Result.Success).data
                        )
                    }
                }
            }
        }
    }

    private fun getErrorState(error: Result.Error): ErrorUiState{
        return when(error.exception.message){
            ErrorMessageType.UNKNOWN_ERROR.name -> ErrorUiState.ErrorData(false, false, true, false, Pair(false, ""))
            ErrorMessageType.MEMBER_NOT_FOUND.name -> ErrorUiState.ErrorData(false, false, false, true, Pair(false, ""))
            ErrorMessageType.EXPIRED_TOKEN.name -> ErrorUiState.ErrorData(true, false, false, false, Pair(false, ""))
            ErrorMessageType.WRONG_TYPE_TOKEN.name -> ErrorUiState.ErrorData(false, true, false, false, Pair(false, ""))
            else -> ErrorUiState.ErrorData(false, false, false, false, Pair(true, error.exception.message))
        }
    }
}