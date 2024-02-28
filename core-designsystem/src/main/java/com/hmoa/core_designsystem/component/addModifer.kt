package com.hmoa.core_designsystem.component

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.addModifier(modifier: Modifier?): Modifier {
    if(modifier != null){
        return this.then(modifier)
    }
    else{
        return this
    }
}