package com.example.colorthing

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("background")
fun bindColor(imgView: ImageView, color: Int){
    imgView.background = ColorDrawable(color)
}