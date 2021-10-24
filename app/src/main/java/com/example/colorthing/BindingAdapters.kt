package com.example.colorthing

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.lifecycle.LiveData

@BindingAdapter("background")
fun setBackground(imgView: ImageView, color: LiveData<Int>){
    Log.i("BindingAdapter", "in setBackground")
    imgView.background = ColorDrawable(color.value!!)
}

@BindingAdapter("value")
fun setValue(numberPicker: NumberPicker, value: LiveData<Int>){
    numberPicker.minValue = 0
    numberPicker.maxValue = 255
    val colorPiece = value.value
    if (colorPiece != null){
        numberPicker.value = colorPiece
    } else {
        numberPicker.value = 0
    }
}
//
//@InverseBindingAdapter("value")
//fun getValue(numberPicker: NumberPicker) : Int{
//   return numberPicker.value
//}