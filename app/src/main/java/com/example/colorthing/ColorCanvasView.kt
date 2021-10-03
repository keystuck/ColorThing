package com.example.colorthing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class ColorCanvasView(context: Context, var bitmap: Bitmap): View(context) {
//    private lateinit var extraBitmap: Bitmap
    private lateinit var extraCanvas: Canvas

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
//        if (::extraBitmap.isInitialized) extraBitmap.recycle()

//        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, 0f, 0f, null)
    }
}