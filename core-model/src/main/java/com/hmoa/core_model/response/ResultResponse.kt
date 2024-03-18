data class ResultResponse<T>(
    var data: T? = null,
    var errorCode: Int? = null,
    var errorMessage: String? = null
)