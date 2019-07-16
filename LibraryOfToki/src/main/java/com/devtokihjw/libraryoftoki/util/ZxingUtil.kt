package com.devtokihjw.libraryoftoki.util

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import com.devtokihjw.libraryoftoki.zxing.MultiFormatWriter
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType

import com.google.zxing.WriterException
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*

const val REQUEST_CODE = 1
const val RESPONSE_CODE = 2
const val KEY_QRCODE_TEXT = "KEY_QRCODE_TEXT"

@Throws(WriterException::class)
fun String.buildQRCodeWithBitmap(size: Int, logo: Bitmap): Bitmap {
    val hints = Hashtable<EncodeHintType, String>()
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H.toString()
    val mBitMatrix = MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE, size, size, hints)

    val width = mBitMatrix.width
    val height = mBitMatrix.height
    val halfW = width / 2
    val halfH = height / 2

    val mMatrix = Matrix()
    val sx = 2.toFloat() * size / 10 / logo.width
    val sy = 2.toFloat() * size / 10 / logo.height
    mMatrix.setScale(sx, sy)
    //设置缩放信息
    //将logo图片按martix设置的信息缩放
    val mLogo = Bitmap.createBitmap(logo, 0, 0, logo.width, logo.height, mMatrix, false)

    val pixels = IntArray(size * size)
    for (y in 0 until size) {
        for (x in 0 until size) {
            if (x > halfW - size / 10 && x < halfW + size / 10
                    && y > halfH - size / 10
                    && y < halfH + size / 10) {
                //该位置用于存放图片信息
                //记录图片每个像素信息
                pixels[y * width + x] = mLogo.getPixel(x - halfW + size / 10, y - halfH + size / 10)
            } else {
                if (mBitMatrix.get(x, y)) {
                    pixels[y * size + x] = Color.BLACK
                } else {
                    pixels[y * size + x] = Color.WHITE
                }
            }
        }
    }
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
    return bitmap
}

@Throws(WriterException::class)
fun String.buildBarcode(width: Int, height: Int): Bitmap {
    val hints = Hashtable<EncodeHintType, String>()
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    val mBitMatrix = MultiFormatWriter().encode(this, BarcodeFormat.CODE_128, width, height, hints)
    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        for (x in 0 until width) {
            if (mBitMatrix.get(x, y)) {
                pixels[y * width + x] = Color.BLACK
            } else {
                pixels[y * width + x] = Color.WHITE
            }
        }
    }

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap
}

fun Activity.onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, func: (String) -> Unit) {
    when (requestCode) {
        REQUEST_CODE -> {
            when (resultCode) {
                RESPONSE_CODE -> {
                    data?.getStringExtra(KEY_QRCODE_TEXT)?.let { code ->
                        printLog("onActivityResult", "QRCode = $code")
                        func(code)
                    }
                }
            }
        }
    }
}