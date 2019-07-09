package com.devtokihjw.libraryoftoki.bottomSheetDialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment(private val mContentLayoutId: Int = 0) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (mContentLayoutId != 0) {
            inflater.inflate(mContentLayoutId, container, false)
        } else {
            null
        }
    }
}