package com.hyangmoa.feature_hbti.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState;", "", "Loading", "NotePickData", "Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState$Loading;", "Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState$NotePickData;", "feature-hbti_debug"})
public abstract interface NotePickUiState {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState$Loading;", "Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "feature-hbti_debug"})
    public static final class Loading implements com.hyangmoa.feature_hbti.viewmodel.NotePickUiState {
        @org.jetbrains.annotations.NotNull
        public static final com.hyangmoa.feature_hbti.viewmodel.NotePickUiState.Loading INSTANCE = null;
        
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\nH\u00c6\u0003J9\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u00d6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001f"}, d2 = {"Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState$NotePickData;", "Lcom/hmoa/feature_hbti/viewmodel/NotePickUiState;", "topRecommendedNote", "", "noteProductList", "Lcom/hmoa/core_model/response/ProductListResponseDto;", "noteSelectData", "", "Lcom/hmoa/core_model/data/NoteSelect;", "noteOrderIndex", "", "(Ljava/lang/String;Lcom/hmoa/core_model/response/ProductListResponseDto;Ljava/util/List;I)V", "getNoteOrderIndex", "()I", "getNoteProductList", "()Lcom/hmoa/core_model/response/ProductListResponseDto;", "getNoteSelectData", "()Ljava/util/List;", "getTopRecommendedNote", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "toString", "feature-hbti_debug"})
    public static final class NotePickData implements com.hyangmoa.feature_hbti.viewmodel.NotePickUiState {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String topRecommendedNote = null;
        @org.jetbrains.annotations.Nullable
        private final com.hyangmoa.core_model.response.ProductListResponseDto noteProductList = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.hyangmoa.core_model.data.NoteSelect> noteSelectData = null;
        private final int noteOrderIndex = 0;
        
        public NotePickData(@org.jetbrains.annotations.NotNull
        java.lang.String topRecommendedNote, @org.jetbrains.annotations.Nullable
        com.hyangmoa.core_model.response.ProductListResponseDto noteProductList, @org.jetbrains.annotations.NotNull
        java.util.List<com.hyangmoa.core_model.data.NoteSelect> noteSelectData, int noteOrderIndex) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getTopRecommendedNote() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.hyangmoa.core_model.response.ProductListResponseDto getNoteProductList() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.hyangmoa.core_model.data.NoteSelect> getNoteSelectData() {
            return null;
        }
        
        public final int getNoteOrderIndex() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.hyangmoa.core_model.response.ProductListResponseDto component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.hyangmoa.core_model.data.NoteSelect> component3() {
            return null;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.hyangmoa.feature_hbti.viewmodel.NotePickUiState.NotePickData copy(@org.jetbrains.annotations.NotNull
        java.lang.String topRecommendedNote, @org.jetbrains.annotations.Nullable
        com.hyangmoa.core_model.response.ProductListResponseDto noteProductList, @org.jetbrains.annotations.NotNull
        java.util.List<com.hyangmoa.core_model.data.NoteSelect> noteSelectData, int noteOrderIndex) {
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
}