package com.hyangmoa.feature_fcm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007\u00a8\u0006\b"}, d2 = {"Lcom/hmoa/feature_fcm/AlarmUiState;", "", "Error", "Loading", "Success", "Lcom/hmoa/feature_fcm/AlarmUiState$Error;", "Lcom/hmoa/feature_fcm/AlarmUiState$Loading;", "Lcom/hmoa/feature_fcm/AlarmUiState$Success;", "feature-fcm_debug"})
public abstract interface AlarmUiState {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/hmoa/feature_fcm/AlarmUiState$Error;", "Lcom/hmoa/feature_fcm/AlarmUiState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "feature-fcm_debug"})
    public static final class Error implements com.hyangmoa.feature_fcm.AlarmUiState {
        @org.jetbrains.annotations.NotNull
        public static final com.hyangmoa.feature_fcm.AlarmUiState.Error INSTANCE = null;
        
        private Error() {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/hmoa/feature_fcm/AlarmUiState$Loading;", "Lcom/hmoa/feature_fcm/AlarmUiState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "feature-fcm_debug"})
    public static final class Loading implements com.hyangmoa.feature_fcm.AlarmUiState {
        @org.jetbrains.annotations.NotNull
        public static final com.hyangmoa.feature_fcm.AlarmUiState.Loading INSTANCE = null;
        
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u000bH\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0015"}, d2 = {"Lcom/hmoa/feature_fcm/AlarmUiState$Success;", "Lcom/hmoa/feature_fcm/AlarmUiState;", "alarms", "", "Lcom/hmoa/core_model/response/AlarmResponse;", "(Ljava/util/List;)V", "getAlarms", "()Ljava/util/List;", "checkAlarm", "", "id", "", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "feature-fcm_debug"})
    public static final class Success implements com.hyangmoa.feature_fcm.AlarmUiState {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.hyangmoa.core_model.response.AlarmResponse> alarms = null;
        
        public Success(@org.jetbrains.annotations.NotNull
        java.util.List<com.hyangmoa.core_model.response.AlarmResponse> alarms) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.hyangmoa.core_model.response.AlarmResponse> getAlarms() {
            return null;
        }
        
        public final void checkAlarm(int id) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.hyangmoa.core_model.response.AlarmResponse> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.hyangmoa.feature_fcm.AlarmUiState.Success copy(@org.jetbrains.annotations.NotNull
        java.util.List<com.hyangmoa.core_model.response.AlarmResponse> alarms) {
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