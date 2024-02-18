import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_network.CommunityComment.CommunityCommentService
import io.ktor.client.*
import org.junit.Test
import org.mockito.Mock

class TestHttpClient {

    @Mock
    private lateinit var httpClient: HttpClient

    @Mock
    private lateinit var communityCommentService: CommunityCommentService

    @Test
    suspend fun `getPerfumeTopDetail 요청`() {
        val communityId = 1
        val page = 1
        val response = CommunityCommentAllResponseDto(
            commentCount = 2,
            comments = listOf(
                CommunityCommentDefaultResponseDto(
                    author = "이용인",
                    commentId = 1,
                    content = "플로리스 향수는 없나요",
                    profileImg = "ewoie9e8.jpeg",
                    time = "20:20:20",
                    writed = true
                ),
                CommunityCommentDefaultResponseDto("서호준", 2, "조말론 향수 추천 좀", "43k4u3hu4.jpeg", "20:30:40", true)
            )
        )
        //given
//        Mockito.`when`(communityCommentService.getCommunityComments()).then(
//            return@when
//        )

    }


}