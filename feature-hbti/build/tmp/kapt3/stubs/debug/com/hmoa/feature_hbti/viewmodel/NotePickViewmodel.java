package com.hyangmoa.feature_hbti.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010!\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020\u000eJ\u0011\u0010&\u001a\u00020\"H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\'J\u0011\u0010(\u001a\u00020\"H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\'J.\u0010)\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020\u000bJ\u0010\u0010,\u001a\u00020\"2\b\u0010-\u001a\u0004\u0018\u00010\u0010J:\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000e0/2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u000e0/2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020\u000e2\u0006\u00101\u001a\u00020\u000bJ\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u000e0/J\u0006\u00103\u001a\u00020\"R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u0019\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u001a0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00064"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/NotePickViewmodel;", "Landroidx/lifecycle/ViewModel;", "surveyRepository", "Lcom/hmoa/core_domain/repository/SurveyRepository;", "hshopRepository", "Lcom/hmoa/core_domain/repository/HshopRepository;", "(Lcom/hmoa/core_domain/repository/SurveyRepository;Lcom/hmoa/core_domain/repository/HshopRepository;)V", "_isCompletedNoteSelected", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_noteOrderIndex", "", "_noteSelectDataState", "", "Lcom/hmoa/core_model/data/NoteSelect;", "_notesState", "Lcom/hmoa/core_model/response/ProductListResponseDto;", "_topRecommendedNoteState", "", "errorUiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/hmoa/core_common/ErrorUiState;", "getErrorUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "expiredTokenErrorState", "generalErrorState", "Lkotlin/Pair;", "isCompletedNoteSelected", "uiState", "Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState;", "getUiState", "unLoginedErrorState", "wrongTypeTokenErrorState", "changeNoteSelectData", "", "index", "value", "data", "getNoteProducts", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTopRecommendedNote", "handleNoteSelectData", "noteOrderQuantity", "selectedNotesOrderQuantity", "initializeIsNoteSelectedList", "noteList", "modifyClickedNoteData", "", "updatedList", "count", "modifySelectedIndexBeforeClick", "postNoteSelected", "feature-hbti_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class NotePickViewmodel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.hyangmoa.core_domain.repository.SurveyRepository surveyRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.hyangmoa.core_domain.repository.HshopRepository hshopRepository = null;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _topRecommendedNoteState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<com.hyangmoa.core_model.response.ProductListResponseDto> _notesState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.hyangmoa.core_model.data.NoteSelect>> _noteSelectDataState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _noteOrderIndex;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isCompletedNoteSelected;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isCompletedNoteSelected = null;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> expiredTokenErrorState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> wrongTypeTokenErrorState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> unLoginedErrorState;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.MutableStateFlow<kotlin.Pair<java.lang.Boolean, java.lang.String>> generalErrorState;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.viewmodel.NotePickUiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.core_common.ErrorUiState> errorUiState = null;
    
    @javax.inject.Inject
    public NotePickViewmodel(@org.jetbrains.annotations.NotNull
    com.hyangmoa.core_domain.repository.SurveyRepository surveyRepository, @org.jetbrains.annotations.NotNull
    com.hyangmoa.core_domain.repository.HshopRepository hshopRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isCompletedNoteSelected() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.feature_hbti.viewmodel.NotePickUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.hyangmoa.core_common.ErrorUiState> getErrorUiState() {
        return null;
    }
    
    public final void initializeIsNoteSelectedList(@org.jetbrains.annotations.Nullable
    com.hyangmoa.core_model.response.ProductListResponseDto noteList) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getTopRecommendedNote(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getNoteProducts(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void postNoteSelected() {
    }
    
    public final void handleNoteSelectData(int index, boolean value, @org.jetbrains.annotations.NotNull
    com.hyangmoa.core_model.data.NoteSelect data, int noteOrderQuantity, int selectedNotesOrderQuantity) {
    }
    
    public final void changeNoteSelectData(int index, boolean value, @org.jetbrains.annotations.NotNull
    com.hyangmoa.core_model.data.NoteSelect data) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.hyangmoa.core_model.data.NoteSelect> modifySelectedIndexBeforeClick() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.hyangmoa.core_model.data.NoteSelect> modifyClickedNoteData(@org.jetbrains.annotations.NotNull
    java.util.List<com.hyangmoa.core_model.data.NoteSelect> updatedList, int index, boolean value, @org.jetbrains.annotations.NotNull
    com.hyangmoa.core_model.data.NoteSelect data, int count) {
        return null;
    }
}