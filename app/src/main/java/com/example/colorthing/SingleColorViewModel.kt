package com.example.colorthing

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SingleColorViewModel(color: Int, app:Application) : AndroidViewModel(app) {

    private val _selectedColor = MutableLiveData<Int>()

    private val _selectedAlpha = MutableLiveData<Int>()
    private val _selectedRed = MutableLiveData<Int>()
    private val _selectedGreen = MutableLiveData<Int>()
    private val _selectedBlue = MutableLiveData<Int>()

    val selectedColor: LiveData<Int>
        get() = _selectedColor

    val selectedAlpha: LiveData<Int>
        get() = _selectedAlpha

    val selectedRed: LiveData<Int>
        get() = _selectedRed

    val selectedGreen: LiveData<Int>
        get() = _selectedGreen

    val selectedBlue: LiveData<Int>
        get() = _selectedBlue

    init{
        _selectedColor.value = color
        _selectedAlpha.value = Color.alpha(color)
        _selectedRed.value = Color.red(color)
        _selectedGreen.value = Color.green(color)
        _selectedBlue.value = Color.blue(color)
        Log.i("SingleColorViewModel", "red color is ${selectedRed.value}")
    }
}