package com.devtokihjw.libraryoftoki.zxing

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.aztec.AztecWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.datamatrix.DataMatrixWriter
import com.google.zxing.oned.*
import com.google.zxing.pdf417.PDF417Writer

class MultiFormatWriter : Writer {

    @Throws(WriterException::class)
    override fun encode(contents: String, format: BarcodeFormat, width: Int, height: Int) = encode(contents, format, width, height, null)

    @Throws(WriterException::class)
    override fun encode(contents: String, format: BarcodeFormat, width: Int, height: Int, hints: Map<EncodeHintType, *>?): BitMatrix {
        val writer: Writer
        when (format) {
            BarcodeFormat.EAN_8 -> writer = EAN8Writer()
            BarcodeFormat.UPC_E -> writer = UPCEWriter()
            BarcodeFormat.EAN_13 -> writer = EAN13Writer()
            BarcodeFormat.UPC_A -> writer = UPCAWriter()
            BarcodeFormat.QR_CODE -> writer = QRCodeWriter()
            BarcodeFormat.CODE_39 -> writer = Code39Writer()
            BarcodeFormat.CODE_93 -> writer = Code93Writer()
            BarcodeFormat.CODE_128 -> writer = Code128Writer()
            BarcodeFormat.ITF -> writer = ITFWriter()
            BarcodeFormat.PDF_417 -> writer = PDF417Writer()
            BarcodeFormat.CODABAR -> writer = CodaBarWriter()
            BarcodeFormat.DATA_MATRIX -> writer = DataMatrixWriter()
            BarcodeFormat.AZTEC -> writer = AztecWriter()
            else -> throw IllegalArgumentException("No encoder available for format $format")
        }
        return writer.encode(contents, format, width, height, hints)
    }
}