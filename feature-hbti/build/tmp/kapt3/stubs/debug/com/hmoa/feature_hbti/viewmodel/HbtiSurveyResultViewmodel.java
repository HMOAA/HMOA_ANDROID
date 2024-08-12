package com.hyangmoa.feature_hbti.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0011\u0010\u001b\u001a\u00020\u001cH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ\u0011\u0010\u001e\u001a\u00020\u001cH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dR\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u0014\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\f0\u00150\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001f"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyResultViewmodel;", "Landroidx/lifecycle/ViewModel;", "memberRepository", "Lcom/hmoa/core_domain/repository/MemberRepository;", "surveyRepository", "Lcom/hmoa/core_domain/repository/SurveyRepository;", "(Lcom/hmoa/core_domain/repository/MemberRepository;Lcom/hmoa/core_domain/repository/SurveyRepository;)V", "_surveyResultState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/hmoa/core_model/request/NoteResponseDto;", "_userNameState", "", "errorUiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/hmoa/core_common/ErrorUiState;", "getErrorUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "expiredTokenErrorState", "", "generalErrorState", "Lkotlin/Pair;", "uiState", "Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyResultUiState;", "getUiState", "unLoginedErrorState", "wrongTypeTokenErrorState", "getSurveyResult", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserName", "feature-hbti_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class HbtiSurveyResultViewmodel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.hyangmoa.core_domain.repository.MemberRepository memberRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.hyangmoa.core_domain.repository.SurveyRepository surveyRepository = null;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.hyangmoa.core_model.request.NoteResponseDto>> _surveyResultState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _userNameState;
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
    private final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.viewmodel.HbtiSurveyResultUiState> uiState = null;
    
    @javax.inject.Inject
    public HbtiSurveyResultViewmodel(@org.jetbrains.annotations.NotNull
    com.hyangmoa.core_domain.repository.MemberRepository memberRepository, @org.jetbrains.annotations.NotNull
    com.hyangmoa.core_domain.repository.SurveyRepository surveyRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.core_common.ErrorUiState> getErrorUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.viewmodel.HbtiSurveyResultUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getUserName(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getSurveyResult(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}