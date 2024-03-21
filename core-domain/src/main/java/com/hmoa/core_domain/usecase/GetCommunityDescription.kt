package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.CommunityRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCommunityDescription @Inject constructor(
    private val repository : CommunityRepository
){

    operator fun invoke(id : Int) = flow{
        emit(repository.getCommunity(id))
    }

}