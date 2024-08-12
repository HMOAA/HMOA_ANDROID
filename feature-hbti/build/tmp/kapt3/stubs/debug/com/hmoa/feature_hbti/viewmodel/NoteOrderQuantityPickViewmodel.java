package com.hyangmoa.feature_hbti.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0007J\u0011\u0010\u001a\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001f"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/NoteOrderQuantityPickViewmodel;", "Landroidx/lifecycle/ViewModel;", "surveyRepository", "Lcom/hmoa/core_domain/repository/SurveyRepository;", "(Lcom/hmoa/core_domain/repository/SurveyRepository;)V", "_isPickCompleted", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_noteOrderQuantityChoice", "Lcom/hmoa/feature_hbti/NoteOrderQuantity;", "_noteOrderQuantityChoiceNameList", "", "", "_topRecommendedNote", "isPickCompleted", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "noteOrderQuantityChoice", "getNoteOrderQuantityChoice", "noteOrderQuantityChoiceList", "uiState", "Lcom/hmoa/feature_hbti/viewmodel/NoteOrderQuantityPickUiState;", "getUiState", "changePickState", "", "isCompleted", "getTopRecommendedNote", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveNoteOrderQuantityChoice", "choiceIndex", "", "feature-hbti_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class NoteOrderQuantityPickViewmodel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.hyangmoa.core_domain.repository.SurveyRepository surveyRepository = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.hyangmoa.feature_hbti.NoteOrderQuantity> noteOrderQuantityChoiceList = null;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _topRecommendedNote;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _noteOrderQuantityChoiceNameList;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<com.hyangmoa.feature_hbti.NoteOrderQuantity> _noteOrderQuantityChoice;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.NoteOrderQuantity> noteOrderQuantityChoice = null;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPickCompleted;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPickCompleted = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.viewmodel.NoteOrderQuantityPickUiState> uiState = null;
    
    @javax.inject.Inject
    public NoteOrderQuantityPickViewmodel(@org.jetbrains.annotations.NotNull
    com.hyangmoa.core_domain.repository.SurveyRepository surveyRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.NoteOrderQuantity> getNoteOrderQuantityChoice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPickCompleted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.viewmodel.NoteOrderQuantityPickUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getTopRecommendedNote(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void saveNoteOrderQuantityChoice(int choiceIndex) {
    }
    
    public final void changePickState(boolean isCompleted) {
    }
}