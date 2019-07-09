package com.devtokihjw.libraryoftoki.zxing

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import com.google.zxing.qrcode.encoder.QRCode
import kotlin.math.max

/**QRCode去除白邊*/
class QRCodeWriter : Writer {

    @Throws(WriterException::class)
    override fun encode(contents: String, format: BarcodeFormat, width: Int, height: Int) = encode(contents, format, width, height, null)

    @Throws(WriterException::class)
    override fun encode(contents: String, format: BarcodeFormat, width: Int, height: Int, hints: Map<EncodeHintType, *>?): BitMatrix {
        if (contents.isEmpty()) {
            throw IllegalArgumentException("Found empty contents")
        }

        if (format != BarcodeFormat.QR_CODE) {
            throw IllegalArgumentException("Can only encode QR_CODE, but got $format")
        }

        if (width < 0 || height < 0) {
            throw IllegalArgumentException("Requested dimensions are too small: " + width + 'x'.toString() + height)
        }

        var errorCorrectionLevel = ErrorCorrectionLevel.L

        if (hints != null) {
            if (hints.containsKey(EncodeHintType.ERROR_CORRECTION)) {
                errorCorrectionLevel = ErrorCorrectionLevel.valueOf(hints[EncodeHintType.ERROR_CORRECTION].toString())
            }
        }

        val code = Encoder.encode(contents, errorCorrectionLevel, hints)
        return renderResult(code, width, height)
    }

    // Note that the input matrix uses 0 == white, 1 == black, while the output matrix uses
    // 0 == black, 255 == white (i.e. an 8 bit greyscale bitmap).
    private fun renderResult(code: QRCode, width: Int, height: Int): BitMatrix {
        val input = code.matrix ?: throw IllegalStateException()
        val inputWidth = input.width
        val inputHeight = input.height
        val outputWidth = max(width, inputWidth)
        val outputHeight = max(height, inputHeight)
        val multiple = Math.min(outputWidth / inputWidth, outputHeight / inputHeight)
        // Padding includes both the quiet zone and the extra white pixels to accommodate the requested
        // dimensions. For example, if input is 25x25 the QR will be 33x33 including the quiet zone.
        // If the requested size is 200x160, the multiple will be 4, for a QR of 132x132. These will
        // handle all the padding from 100x100 (the actual QR) up to 200x160.
        val leftPadding = (outputWidth - inputWidth * multiple) / 2
        val topPadding = (outputHeight - inputHeight * multiple) / 2
        val output = BitMatrix(outputWidth, outputHeight)
        var inputY = 0
        var outputY = topPadding
        while (inputY < inputHeight) {
            // Write the contents of this row of the barcode
            var inputX = 0
            var outputX = leftPadding
            while (inputX < inputWidth) {
                if (input.get(inputX, inputY).toInt() == 1) {
                    output.setRegion(outputX, outputY, multiple, multiple)
                }
                inputX++
                outputX += multiple
            }
            inputY++
            outputY += multiple
        }
        return output
    }
}