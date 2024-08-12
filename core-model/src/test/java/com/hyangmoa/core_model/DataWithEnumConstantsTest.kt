package com.hyangmoa.core_model

import com.hyangmoa.core_model.response.CommunityByCategoryResponseDto
import com.hyangmoa.core_model.response.CommunityPhotoDefaultResponseDto
import com.hyangmoa.core_model.response.MemberResponseDto
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName


@DisplayName("enum class 상수이름이 String타입으로 속성으로 들어가는 모델 테스트")
class testDataWithEnumConstants {
    @DisplayName("Category enum class 상수 이름이 String타입으로 category 속성에 들어가는 모델 테스트")
    @Test
    fun isAvailableData_includeCategoryPropertyAsEnumConstants() {
        val communityPhotoDefaultResponseDto = CommunityPhotoDefaultResponseDto(1, "사진1")
        val communityByCategoryResponseDto =
            CommunityByCategoryResponseDto(Category.시향기.name, 1, 1, liked = true, heartCount = 1, title = "시향기")
        val communityByCategoryResponseDto2 =
            CommunityByCategoryResponseDto(Category.추천.name, 1, 1, liked = true, heartCount = 1, title = "추천")


        Assertions.assertEquals("시향기", communityByCategoryResponseDto.category)
        Assertions.assertEquals("추천", communityByCategoryResponseDto2.category)

    }

    @DisplayName("Provider enum class 상수 이름이 String타입으로 provider 속성에 들어가는 모델 테스트")
    @Test
    fun isAvailableData_includeProviderPropertyAsEnumConstants() {
        val memberResponseDto2 = MemberResponseDto(22, 1, "memberImageUrl", "사용자2", Provider.GOOGLE.name, true)
        val memberResponseDto3 = MemberResponseDto(22, 1, "memberImageUrl", "사용자3", Provider.KAKAO.name, true)

        Assertions.assertEquals("GOOGLE", memberResponseDto2.provider)
        Assertions.assertEquals("KAKAO", memberResponseDto3.provider)

    }
}