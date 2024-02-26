package corenetwork.Admin

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto

interface AdminService {
    suspend fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any>
    suspend fun postHomeMenu(homeMenuId: Int, homeMenuSaveRequestDto: HomeMenuSaveRequestDto): DataResponseDto<Any>
    suspend fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any>
    suspend fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any>
}