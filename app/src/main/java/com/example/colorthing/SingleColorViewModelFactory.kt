package com.example.colorthing

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SingleColorViewModelFactory(
    private val selectedColor: Int,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleColorViewModel::class.java)) {
            return SingleColorViewModel(selectedColor, app) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}