import com.hmoa.core_model.data.ErrorMessage

data class ResultResponse<T>(
    var data: T? = null,
    var errorMessage: ErrorMessage? = null
)