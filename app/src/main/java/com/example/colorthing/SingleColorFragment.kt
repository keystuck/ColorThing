package com.example.colorthing

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.example.colorthing.databinding.SingleColorFragmentBinding

class SingleColorFragment : Fragment() {

    private lateinit var viewModel: SingleColorViewModel
    val args: SingleColorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var currentColorAsInt = args.color



        var currentAlpha = Color.alpha(currentColorAsInt)
        var currentRed = Color.red(currentColorAsInt)
        var currentGreen = Color.green(currentColorAsInt)
        var currentBlue = Color.blue(currentColorAsInt)

        var currentColor = Color.argb(currentAlpha, currentRed, currentGreen, currentBlue)


        val viewModelFactory =SingleColorViewModelFactory(currentColor, requireNotNull(activity).application)
        viewModel =ViewModelProvider(this, viewModelFactory).get(SingleColorViewModel::class.java)

        val binding = SingleColorFragmentBinding.inflate(layoutInflater)

        binding.viewmodel = viewModel

        //TODO: Spinner isn't working t all - needs limits set as well as hooking up
        //took binding out of onViewCreated and moved to onCreateView and now
        //binding seems to work... but onCreateView is deprecated, so investigate
        //wrote a bindingAdapter but doesn't seem to be working

        return binding.root
    }




}