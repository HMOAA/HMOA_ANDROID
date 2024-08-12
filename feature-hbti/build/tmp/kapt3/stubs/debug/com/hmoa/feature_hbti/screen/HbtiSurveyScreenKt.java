package com.hyangmoa.feature_hbti.screen;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u001a\u008a\u0001\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0014\u0010\u0005\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0018\u00010\u00032\u0014\u0010\u0006\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0003\u0018\u00010\u000326\u0010\b\u001a2\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001a2\u0010\u0011\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001a<\u0010\u0015\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a\b\u0010\u0018\u001a\u00020\u0001H\u0007\u001a\u0016\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a8\u0006\u001b"}, d2 = {"HbtiSurveyContent", "", "questions", "", "", "optionsContents", "options", "Lcom/hmoa/core_model/response/SurveyOptionResponseDto;", "onClickOption", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "optionId", "page", "onClickFinishSurvey", "Lkotlin/Function0;", "HbtiSurveyRoute", "onErrorHandleLoginAgain", "onBackClick", "onClickHbtiSurveyResultScreen", "HbtiSurveyScreen", "viewModel", "Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyViewmodel;", "HbtiSurveyScreenPreview", "calculateProgressStepSize", "", "feature-hbti_debug"})
public final class HbtiSurveyScreenKt {
    
    public static final float calculateProgressStepSize(@org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> questions) {
        return 0.0F;
    }
    
    @androidx.compose.runtime.Composable
    public static final void HbtiSurveyRoute(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onErrorHandleLoginAgain, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClickHbtiSurveyResultScreen) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void HbtiSurveyScreen(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onErrorHandleLoginAgain, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClickFinishSurvey, @org.jetbrains.annotations.NotNull
    com.hyangmoa.feature_hbti.viewmodel.HbtiSurveyViewmodel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable
    public static final void HbtiSurveyContent(@org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> questions, @org.jetbrains.annotations.Nullable
    java.util.List<? extends java.util.List<java.lang.String>> optionsContents, @org.jetbrains.annotations.Nullable
    java.util.List<? extends java.util.List<com.hyangmoa.core_model.response.SurveyOptionResponseDto>> options, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> onClickOption, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClickFinishSurvey) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview
    @androidx.compose.runtime.Composable
    public static final void HbtiSurveyScreenPreview() {
    }
}