package com.example.colorthing

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import com.example.colorthing.databinding.FragmentColorPickerBinding
import kotlin.math.min
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.doOnLayout
import androidx.navigation.fragment.findNavController


class ColorPickerFragment : Fragment()  {

    private lateinit var binding: FragmentColorPickerBinding


    private var width = 0
    private var colorWidth = 0.0
    private var height = 0
    private var colorHeight = 0.0

    private var currentGreen = 0

    private var restrictedWidth = 0
    private var restrictedHeight = 0

    private var isLongClick = false
    private var pressTime = 0L

    companion object {
        var greenKey = "GREENKEY"
        var colorKey = "COLORKEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(greenKey, currentGreen)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorPickerBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            currentGreen = savedInstanceState.getInt (greenKey)
        } else {
            currentGreen = 0
        }
        binding.backgroundImage.doOnLayout {
            constructBitmap(currentGreen)
        }

        binding.backgroundImage.setOnLongClickListener{

            isLongClick = true
            false
        }

         binding.backgroundImage.setOnTouchListener { v, event ->

             var x = event.getX()
             var y = event.getY()
             var bitmap = binding.backgroundImage.drawable.toBitmap(width=width - restrictedWidth,
                 height = height - restrictedHeight, Bitmap.Config.ARGB_8888)

             var pixelColor = bitmap?.getPixel(x.toInt(), y.toInt())
            if (event.action == MotionEvent.ACTION_DOWN) {
                pressTime = System.currentTimeMillis()



                var redValue = Color.red(pixelColor)
                var greenValue = Color.green(pixelColor)
                var blueValue = Color.blue(pixelColor)


                activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)?.setTitle("R: $redValue," +
                        " G: $greenValue, B: $blueValue")
            }

             if (event.action == MotionEvent.ACTION_UP) {
                 if (System.currentTimeMillis() - pressTime > ViewConfiguration.getLongPressTimeout()){

                     pressTime = 0L

//                     addGreenToSector(event.getX().toInt(), event.getY().toInt())
                     val action = ColorPickerFragmentDirections.actionColorPickerFragmentToSingleColorFragment(pixelColor)

                     Log.i("ColorPickerFragment", "alpha ${Color.alpha(pixelColor)}, red ${Color.red(pixelColor)}")
                this.findNavController().navigate(action)

             }

             }
            true

         }



        // Inflate the layout for this fragment
        return binding.root
    }


    private fun constructBitmap(greenValue: Int){
        currentGreen = greenValue

        height = binding.backgroundImage.height //height is ready
        width = binding.backgroundImage.width

        val colorArray = arrayListOf<Int>()
        colorWidth = width / 256.0

        colorHeight = height / 256.0

        for (j in 0..(height - restrictedHeight - 1) ){

            for (i in 0 .. (width - restrictedWidth - 1) ) {

                    colorArray.add(
                        Color.argb(255,
                            (min((i / colorWidth).toInt(), 255)/16).toInt() * 16 , greenValue,(min((j / colorHeight).toInt(), 255)/16).toInt() * 16 )

                    )

            }


        }



        val colorIntArray = colorArray.toIntArray()


        val tempBitmap = Bitmap.createBitmap(
            colorIntArray,
            width - restrictedWidth,
            height - restrictedHeight,
            Bitmap.Config.ARGB_8888
        )



        binding.backgroundImage.setImageBitmap(tempBitmap)
    }

    fun addGreenToSector(x: Int, y: Int){
        var oldBitmap = binding.backgroundImage.drawable.toBitmap(
            width - restrictedWidth,
            height - restrictedHeight,
                 Bitmap.Config.ARGB_8888)

        var oldBitmapSamplePixel = oldBitmap.getPixel(x, y)
        var colorRed = Color.red(oldBitmapSamplePixel)
        val colorGreen = Color.green(oldBitmapSamplePixel)
        var colorBlue = Color.blue(oldBitmapSamplePixel)
        var newColor = Color.argb(255, 0, 0, 0)
        if (colorGreen < 255){
            newColor = Color.argb(255, colorRed, colorGreen + 16, colorBlue)
        } else {
            newColor = Color.argb(255, colorRed, 0, colorBlue)
        }


        var xStartPosition = min(
            (x/(colorWidth * 16).toInt() * (colorWidth * 16).toInt()),
            width - (colorWidth * 16).toInt())
        var yStartPosition = min(
            (y/(colorHeight * 16).toInt() * (colorHeight * 16).toInt()),
            height - (colorHeight * 16).toInt())
        var newArray = arrayListOf<Int>()
        for (j in 0 .. (16*colorHeight).toInt() - 1) {
            for (i in 0..(16 * colorWidth).toInt() -1 ) {
                newArray.add(newColor)
            }
        }
            var tempArray = newArray.toIntArray()

        var newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true)


       var  widthOfRectangle = (16 * colorWidth).toInt()
        var heightOfRectangle = (16 * colorHeight).toInt()
        var distanceBetween = widthOfRectangle




        newBitmap.setPixels(
            tempArray,
            0,
            distanceBetween,
            xStartPosition, yStartPosition,
            widthOfRectangle,
            heightOfRectangle
        )

        binding.backgroundImage.setImageBitmap(newBitmap)

    }

    fun addGreenToAll(inputGreen:Int){
        var oldBitmap = binding.backgroundImage.drawable.toBitmap(
            width - restrictedWidth,
            height - restrictedHeight,
            Bitmap.Config.ARGB_8888)

        var oldBitmapSamplePixel = oldBitmap.getPixel(0,0)
        val colorGreen = Color.green(oldBitmapSamplePixel)

        var newGreen = 0
        if (inputGreen > 0){
            if (colorGreen < 224) {
                newGreen = colorGreen + inputGreen
            } else if (colorGreen == 224){
                newGreen = 255
            }
        } else if (colorGreen > 31 && inputGreen < 0){
            newGreen = colorGreen + inputGreen
        }
        constructBitmap(newGreen)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater: MenuInflater = inflater
        inflater.inflate(R.menu.color, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_32_green){
            addGreenToAll(32)
            return true
        } else if (item.itemId == R.id.remove_32_green){
            addGreenToAll(-32)
            return true
        } else if (item.itemId == R.id.info){
            showInfoDialog()
            return true
        }
        else return super.onOptionsItemSelected(item)
    }



    fun showInfoDialog(){
        val infoDialog: AlertDialog? = activity?.let{
            val builder: AlertDialog.Builder? = AlertDialog.Builder(it)

            builder?.setMessage(R.string.details)
                ?.setPositiveButton(R.string.OK, null)

            builder?.create()
        }
        infoDialog?.show()
    }
}