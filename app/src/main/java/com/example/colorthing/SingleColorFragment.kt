package com.example.colorthing

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.colorthing.databinding.SingleColorFragmentBinding

class SingleColorFragment : Fragment() {

    private lateinit var viewModel: SingleColorViewModel
    val args: SingleColorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_color_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var currentColorAsInt = args.color



        var currentAlpha = Color.alpha(currentColorAsInt)
        var currentRed = Color.red(currentColorAsInt)
        var currentGreen = Color.green(currentColorAsInt)
        var currentBlue = Color.blue(currentColorAsInt)

        var currentColor = Color.argb(currentAlpha, currentRed, currentGreen, currentBlue)

        Log.i("SingleColorFragment", "alpha $currentAlpha red $currentRed")

        val viewModelFactory =SingleColorViewModelFactory(currentColor, requireNotNull(activity).application)
        viewModel =ViewModelProvider(this, viewModelFactory).get(SingleColorViewModel::class.java)

        val binding = SingleColorFragmentBinding.inflate(layoutInflater)

        binding.viewmodel = viewModel

    }


}