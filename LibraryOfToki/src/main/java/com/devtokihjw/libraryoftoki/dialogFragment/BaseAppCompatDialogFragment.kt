package com.devtokihjw.libraryoftoki.dialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment

abstract class BaseAppCompatDialogFragment(private val mContentLayoutId: Int = 0) : AppCompatDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (mContentLayoutId != 0) {
            inflater.inflate(mContentLayoutId, container, false)
        } else {
            null
        }
    }

    override fun onResume() {
        super.onResume()
        doWindow()
    }

    private fun doWindow() {
        dialog?.window?.let { mWindow ->
            val mLayoutParams = mWindow.attributes
            mLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            mWindow.attributes = mLayoutParams
        }
    }
}