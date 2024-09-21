package com.hmoa.core_domain.usecase

import ResultResponse
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_model.data.MagazineContentItem
import com.hmoa.core_model.data.MagazineSuccessItem
import javax.inject.Inject

class GetMagazineDescription @Inject constructor(
    private val magazineRepository: MagazineRepository
) {
    suspend operator fun invoke(magazineId : Int) : ResultResponse<MagazineSuccessItem>{
        val response = magazineRepository.getMagazine(magazineId)
        val result = ResultResponse<MagazineSuccessItem>()
        if (response.errorMessage != null) {
            result.errorMessage = response.errorMessage
        } else {
            val data = response.data!!

            //content 데이터 정리
            val contents = mutableListOf<MagazineContentItem>()
            for(x in 0 until data.contents.size){
                contents.add(MagazineContentItem())
            }
            var curIdx = 0
            data.contents.forEachIndexed{ idx, value ->
                if(value.type == "content"){
                    contents[curIdx].content = value.data
                } else if(value.type == "header") {
                    contents[curIdx].header = value.data
                } else if(value.type == "image"){
                    contents[curIdx].image = value.data
                }
                if(contents[curIdx].content != null && contents[curIdx].header != null){
                    curIdx += 1
                }
            }

            //날짜 데이터 형식 변경
            val month = when(data.createAt.split(" ")[0]){
                "Jan" -> 1
                "Feb" -> 2
                "Mar" -> 3
                "Apr" -> 4
                "May" -> 5
                "Jun" -> 6
                "Jul" -> 7
                "Aug" -> 8
                "Sep" -> 9
                "Oct" -> 10
                "Nov" -> 11
                "Dec" -> 12
                else -> 0
            }
            val day = data.createAt.split(" ")[1].replace(",", "")
            val year = data.createAt.split(" ")[2]
            val newDate = "${year}.${month}.${day}"

            //preview 개행 제거
            val preview = data.preview.replace("\\n", "")

            result.data = MagazineSuccessItem(
                title = data.title,
                createAt = newDate,
                previewImgUrl = data.previewImgUrl,
                contents = contents,
                preview = preview,
                tags = data.tags,
                viewCount = data.viewCount,
                likeCount = data.likeCount,
                isLiked = data.liked
            )
        }
        return result
    }
}