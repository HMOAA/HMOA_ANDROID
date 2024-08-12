package com.hyangmoa.feature_hbti.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyUiState;", "", "HbtiData", "Loading", "Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyUiState$HbtiData;", "Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyUiState$Loading;", "feature-hbti_debug"})
public abstract interface HbtiSurveyUiState {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u0014\u0010\u0005\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0018\u00010\u0003\u0012\u0014\u0010\u0006\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0003\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ\u0011\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0017\u0010\u0012\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0018\u00010\u0003H\u00c6\u0003J\u0017\u0010\u0013\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0003\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\tH\u00c6\u0003JW\u0010\u0015\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0016\b\u0002\u0010\u0005\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0018\u00010\u00032\u0016\b\u0002\u0010\u0006\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0003\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0004H\u00d6\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001f\u0010\u0006\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0003\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001f\u0010\u0005\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u001d"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyUiState$HbtiData;", "Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyUiState;", "questions", "", "", "optionsContent", "options", "Lcom/hmoa/core_model/response/SurveyOptionResponseDto;", "answers", "Lcom/hmoa/core_model/request/SurveyRespondRequestDto;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/hmoa/core_model/request/SurveyRespondRequestDto;)V", "getAnswers", "()Lcom/hmoa/core_model/request/SurveyRespondRequestDto;", "getOptions", "()Ljava/util/List;", "getOptionsContent", "getQuestions", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "feature-hbti_debug"})
    public static final class HbtiData implements com.hyangmoa.feature_hbti.viewmodel.HbtiSurveyUiState {
        @org.jetbrains.annotations.Nullable
        private final java.util.List<java.lang.String> questions = null;
        @org.jetbrains.annotations.Nullable
        private final java.util.List<java.util.List<java.lang.String>> optionsContent = null;
        @org.jetbrains.annotations.Nullable
        private final java.util.List<java.util.List<com.hyangmoa.core_model.response.SurveyOptionResponseDto>> options = null;
        @org.jetbrains.annotations.Nullable
        private final com.hyangmoa.core_model.request.SurveyRespondRequestDto answers = null;
        
        public HbtiData(@org.jetbrains.annotations.Nullable
        java.util.List<java.lang.String> questions, @org.jetbrains.annotations.Nullable
        java.util.List<? extends java.util.List<java.lang.String>> optionsContent, @org.jetbrains.annotations.Nullable
        java.util.List<? extends java.util.List<com.hyangmoa.core_model.response.SurveyOptionResponseDto>> options, @org.jetbrains.annotations.Nullable
        com.hyangmoa.core_model.request.SurveyRespondRequestDto answers) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.List<java.lang.String> getQuestions() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.List<java.util.List<java.lang.String>> getOptionsContent() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.List<java.util.List<com.hyangmoa.core_model.response.SurveyOptionResponseDto>> getOptions() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.hyangmoa.core_model.request.SurveyRespondRequestDto getAnswers() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.List<java.lang.String> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.List<java.util.List<java.lang.String>> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.List<java.util.List<com.hyangmoa.core_model.response.SurveyOptionResponseDto>> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.hyangmoa.core_model.request.SurveyRespondRequestDto component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.hyangmoa.feature_hbti.viewmodel.HbtiSurveyUiState.HbtiData copy(@org.jetbrains.annotations.Nullable
        java.util.List<java.lang.String> questions, @org.jetbrains.annotations.Nullable
        java.util.List<? extends java.util.List<java.lang.String>> optionsContent, @org.jetbrains.annotations.Nullable
        java.util.List<? extends java.util.List<com.hyangmoa.core_model.response.SurveyOptionResponseDto>> options, @org.jetbrains.annotations.Nullable
        com.hyangmoa.core_model.request.SurveyRespondRequestDto answers) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyUiState$Loading;", "Lcom/hmoa/feature_hbti/viewmodel/HbtiSurveyUiState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "feature-hbti_debug"})
    public static final class Loading implements com.hyangmoa.feature_hbti.viewmodel.HbtiSurveyUiState {
        @org.jetbrains.annotations.NotNull
        public static final com.hyangmoa.feature_hbti.viewmodel.HbtiSurveyUiState.Loading INSTANCE = null;
        
        private Loading() {
            super();
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}