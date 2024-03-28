data class ResultResponse<T>(
    var data: T? = null,
    var exception: Exception? = null
)