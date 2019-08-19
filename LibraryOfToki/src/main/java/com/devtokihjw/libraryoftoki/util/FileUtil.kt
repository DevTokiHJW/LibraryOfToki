package com.devtokihjw.libraryoftoki.util

import android.os.AsyncTask
import okhttp3.ResponseBody
import java.io.*

fun String.fileToString(): String {
    val mByteArrayOutputStream = ByteArrayOutputStream().writeOutputStream(FileInputStream(this), null, 0)
    return String(mByteArrayOutputStream.toByteArray(), Charsets.UTF_8)
}

fun <T : OutputStream> T.writeOutputStream(inputStream: InputStream, progressUpdate: ((Int) -> Unit)?, fileSize: Long): T {
    val mByteArray = ByteArray(1024)
    var tmpLength: Int
    var loadSize = 0
    while (true) {
        tmpLength = inputStream.read(mByteArray)
        if (tmpLength == -1) {
            break
        }
        write(mByteArray, 0, tmpLength)
        loadSize += tmpLength
        progressUpdate?.let { progressUpdate((loadSize * 100 / fileSize).toInt()) }
    }
    flush()
    close()
    inputStream.close()
    return this
}

class SaveFileTask(private val filePath: String, private val fileName: String, private val responseBody: ResponseBody, private val preExecute: () -> Unit, private val progressUpdate: (Int) -> Unit, private val success: (Boolean, String?) -> Unit) : AsyncTask<Any, Int, Any>() {

    override fun onPreExecute() {
        preExecute()
    }

    override fun doInBackground(vararg p0: Any?): Any? {
        printLog("filePath", "$filePath$fileName")
        val mFileOutputStream = FileOutputStream("$filePath$fileName")
        mFileOutputStream.writeOutputStream(responseBody.byteStream(), {
            publishProgress(it)
        }, responseBody.contentLength())
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        values[0]?.let { progress ->
            progressUpdate(progress)
        }
    }

    override fun onPostExecute(result: Any?) {
        success(true, null)
    }
}