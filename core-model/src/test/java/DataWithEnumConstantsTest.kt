import com.hmoa.core_model.Category
import com.hmoa.core_model.Provider
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.CommunityPhotoDefaultResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("enum class 상수이름이 String타입으로 속성으로 들어가는 모델 테스트")
class DataWithEnumConstantsTest {
    @DisplayName("Category enum class 상수 이름이 String타입으로 category 속성에 들어가는 모델 테스트")
    @Test
    fun isAvailableData_includeCategoryPropertyAsEnumConstants() {
        val communityPhotoDefaultResponseDto = CommunityPhotoDefaultResponseDto(1, "사진1")
        val communityByCategoryResponseDto = CommunityByCategoryResponseDto(Category.시향기.name, 1, "조말론 넥타르 블라썸 좋아요")
        val communityByCategoryResponseDto2 = CommunityByCategoryResponseDto(Category.추천.name, 1, "조말론 넥타르 블라썸 좋아요")
        val communityDefaultResponseDto = CommunityDefaultResponseDto(
            "사용자1",
            Category.자유.name,
            communityPhotoDefaultResponseDto,
            "구찌 알케미스트 어쩌구 추천",
            1,
            1,
            "myProfileImgUrl",
            "profileImgUrl",
            "00:00:00",
            "향수 추천",
            true
        )

        Assertions.assertEquals("시향기", communityByCategoryResponseDto.category)
        Assertions.assertEquals("추천", communityByCategoryResponseDto2.category)
        Assertions.assertEquals("자유", communityDefaultResponseDto.category)

    }

    @DisplayName("Provider enum class 상수 이름이 String타입으로 provider 속성에 들어가는 모델 테스트")
    @Test
    fun isAvailableData_includeProviderPropertyAsEnumConstants() {
        val memberResponseDto1 = MemberResponseDto(22, 1, "memberImageUrl", "사용자1", Provider.APPLE.name, true)
        val memberResponseDto2 = MemberResponseDto(22, 1, "memberImageUrl", "사용자2", Provider.GOOGLE.name, true)
        val memberResponseDto3 = MemberResponseDto(22, 1, "memberImageUrl", "사용자3", Provider.KAKAO.name, true)

        Assertions.assertEquals("APPLE", memberResponseDto1.provider)
        Assertions.assertEquals("GOOGLE", memberResponseDto2.provider)
        Assertions.assertEquals("KAKAO", memberResponseDto3.provider)

    }
}