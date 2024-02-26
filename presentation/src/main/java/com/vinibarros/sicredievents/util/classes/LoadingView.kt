package com.vinibarros.sicredievents.util.classes

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.vinibarros.sicredievents.R

@SuppressLint("ClickableViewAccessibility")
class LoadingView @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null) :
    RelativeLayout(context, attributeSet) {

    init {
        visibility = View.GONE
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.loading_view, this)
        this.setOnClickListener(null)
        this.setOnTouchListener { _, _ -> true }
    }
}