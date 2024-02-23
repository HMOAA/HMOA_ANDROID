class TestHttpClient {
//    @Inject
//    val httpClient = HttpClient()
//
//    @Test
//    suspend fun TestAddApplicationJsonHeader() {
//        //TODO("header(HttpHeaders.ContentType, ContentType.Application.Json) 라는 헤더를 HttpClient에 삽입해야함")
//        val httpClientWithHeader = httpClient.config {
//            headers {
//                append(HttpHeaders.ContentType, ContentType.Application.Json)
//            }
//        }
//        //어떻게 입증할지 모름
//    }
//
//    suspend fun TestAddFormUrlEncodedHeader() {
//        //TODO("header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())")
//        httpClient.config {
//            headers {
//                append(HttpHeaders.ContentType, ContentType.Application.Json)
//            }
//        }
//        //어떻게 입증할지 모름
//    }

    suspend fun TestSendApplicationJsonService() {
        //TODO("ContentType.Application.Json로 보내진 api는 요청에 대한 응답을 받아야 함")
    }

    suspend fun TestSendApplicationFormUrlEncodedService() {
        //TODO("ContentType.Application.FormUrlEncoded로 보내진 api는 요청에 대한 응답을 받아야 함")
    }
}