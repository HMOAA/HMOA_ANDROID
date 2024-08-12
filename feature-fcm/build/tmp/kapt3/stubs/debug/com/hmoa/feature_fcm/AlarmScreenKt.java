package com.hyangmoa.feature_fcm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\\\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u000326\u0010\u0005\u001a2\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u001a^\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u001326\u0010\u0005\u001a2\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001aH\u0010\u0015\u001a\u00020\u00012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00172\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007\u001a\b\u0010\u001b\u001a\u00020\u0001H\u0003\u00a8\u0006\u001c"}, d2 = {"AlarmContent", "", "alarms", "", "Lcom/hmoa/core_model/response/AlarmResponse;", "onNavTarget", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "id", "", "Type", "onNavBack", "Lkotlin/Function0;", "AlarmScreen", "uiState", "Lcom/hmoa/feature_fcm/AlarmUiState;", "errState", "Lcom/hmoa/core_common/ErrorUiState;", "type", "AlarmScreenRoute", "onNavCommunityDesc", "Lkotlin/Function1;", "onNavPerfumeComment", "viewModel", "Lcom/hmoa/feature_fcm/AlarmViewModel;", "EmptyScreen", "feature-fcm_debug"})
public final class AlarmScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void AlarmScreenRoute(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavBack, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onNavCommunityDesc, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onNavPerfumeComment, @org.jetbrains.annotations.NotNull
    com.hyangmoa.feature_fcm.AlarmViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AlarmScreen(@org.jetbrains.annotations.NotNull
    com.hyangmoa.feature_fcm.AlarmUiState uiState, @org.jetbrains.annotations.NotNull
    com.hyangmoa.core_common.ErrorUiState errState, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onNavTarget, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavBack) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void AlarmContent(java.util.List<com.hyangmoa.core_model.response.AlarmResponse> alarms, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onNavTarget, kotlin.jvm.functions.Function0<kotlin.Unit> onNavBack) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void EmptyScreen() {
    }
}