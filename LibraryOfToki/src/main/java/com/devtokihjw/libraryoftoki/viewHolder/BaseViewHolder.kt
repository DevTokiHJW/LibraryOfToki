package com.devtokihjw.libraryoftoki.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(mViewGroup: ViewGroup, resId: Int = 0) : RecyclerView.ViewHolder(LayoutInflater.from(mViewGroup.context).inflate(resId, mViewGroup, false)) {

    abstract fun onStart(data: Any)
}