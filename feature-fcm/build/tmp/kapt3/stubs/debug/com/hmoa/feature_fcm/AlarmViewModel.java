package com.hyangmoa.feature_fcm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\"\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/hmoa/feature_fcm/AlarmViewModel;", "Landroidx/lifecycle/ViewModel;", "fcmRepository", "Lcom/hmoa/core_domain/repository/FcmRepository;", "(Lcom/hmoa/core_domain/repository/FcmRepository;)V", "errorUiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/hmoa/core_common/ErrorUiState;", "getErrorUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "expiredTokenErrorState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "generalErrorState", "Lkotlin/Pair;", "", "uiState", "Lcom/hmoa/feature_fcm/AlarmUiState;", "getUiState", "unLoginedErrorState", "wrongTypeTokenErrorState", "checkAlarm", "", "id", "", "feature-fcm_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class AlarmViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.hyangmoa.core_domain.repository.FcmRepository fcmRepository = null;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> expiredTokenErrorState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> wrongTypeTokenErrorState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> unLoginedErrorState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<kotlin.Pair<java.lang.Boolean, java.lang.String>> generalErrorState;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.core_common.ErrorUiState> errorUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_fcm.AlarmUiState> uiState = null;
    
    @javax.inject.Inject
    public AlarmViewModel(@org.jetbrains.annotations.NotNull
    com.hyangmoa.core_domain.repository.FcmRepository fcmRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.core_common.ErrorUiState> getErrorUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_fcm.AlarmUiState> getUiState() {
        return null;
    }
    
    public final void checkAlarm(int id) {
    }
}