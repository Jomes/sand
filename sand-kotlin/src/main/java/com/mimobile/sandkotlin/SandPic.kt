package com.mimobile.sandkotlin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

/**
 * Created by jomeslu on 18-12-1.
 */
class SandPic private constructor() {
    private object Holder {
        val INSTANCE = SandPic()
    }

    private external fun generate(pixels: IntArray, width: Int, height: Int, threshold: Int, ponitNum: Int): IntArray

    fun tramform(bitmap: Bitmap, threshold: Int, ponitNum: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val newImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newImage)
        val paint = Paint()
        paint.isAntiAlias = false
        paint.style = Paint.Style.STROKE
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        val generate = generate(pixels, width, height, threshold, ponitNum)
        var i = 0
        val n = generate.size
        while (i + 1 < n) {
            val x = if (generate[i] > 0) generate[i] else 0
            val y = if (generate[i + 1] > 0) generate[i + 1] else 0
            val color = bitmap.getPixel(x, y)
            paint.color = color
            canvas.drawCircle(x.toFloat(), y.toFloat(), 1f, paint)
            i += 2
        }

        return newImage
    }

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("sand-lib")
        }

        val instance: SandPic by lazy { Holder.INSTANCE }
    }

    var b: String? = null
}