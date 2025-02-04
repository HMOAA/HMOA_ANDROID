package com.hmoa.feature_magazine.ViewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hmoa.core_common.ui.BaseViewModel
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.feature_magazine.MagazinePagingSource
import com.hmoa.feature_magazine.contract.MagazineHomeEffect
import com.hmoa.feature_magazine.contract.MagazineHomeEvent
import com.hmoa.feature_magazine.contract.MagazineHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MagazineMainViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val magazineRepository : MagazineRepository
): BaseViewModel<MagazineHomeEvent, MagazineHomeState, MagazineHomeEffect>() {
    private val magazinePager = Pager(PagingConfig(5)){ MagazinePagingSource(magazineRepository) }
    var magazines: Flow<PagingData<MagazineSummaryResponseDto>> = emptyFlow()
    val perfumeFlow = flow{
        val result = perfumeRepository.getRecentPerfumes()
        if (result.errorMessage != null){
            //error 상태 처리
        }
        emit(result.data!!)
    }

    val communityFlow = flow{
        val result = magazineRepository.getMagazineTastingComment()
        if (result.errorMessage != null){
            //error 상태 처리
        }
        emit(result.data!!)
    }

    init{
        handleEvent(MagazineHomeEvent.LoadMagazines)
    }

    override fun createInitialState(): MagazineHomeState {
        return MagazineHomeState()
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
            }.collect {
                setState{
                    copy(
                        perfumes = it.first,
                        posts = it.second
                    )
                }
            }
        }
    }
}