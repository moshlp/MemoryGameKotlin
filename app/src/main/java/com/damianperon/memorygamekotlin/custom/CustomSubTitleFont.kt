package com.damianperon.memorygamekotlin.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.security.AccessController.getContext

class CustomSubTitleFont (context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    init {
        val typeface = Typeface.createFromAsset(getContext().assets, "bradbunr.ttf")
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB ||
            android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            setTypeface(typeface)
        }
    }
}