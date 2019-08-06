package com.devtokihjw.libraryoftoki.adapter

import androidx.recyclerview.widget.RecyclerView
import com.devtokihjw.libraryoftoki.viewHolder.BaseViewHolder

abstract class BaseRecyclerViewAdapter(val list: MutableList<Any>?) : RecyclerView.Adapter<BaseViewHolder>() {

    var itemClickFunction: ((Any) -> Unit)? = null

    var itemLongClickFunction: ((Any) -> Boolean)? = null

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val data = list?.get(position)
        data?.let {
            holder.onStart(data)
            itemClickFunction?.let { func ->
                holder.itemView.setOnClickListener {
                    func(data)
                }
            }
            itemLongClickFunction?.let { func ->
                holder.itemView.setOnLongClickListener {
                    func(data)
                }
            }
        }
    }

    fun insertData(data: Any) {
        list?.let {
            list.add(data)
            notifyItemInserted(itemCount - 1)
        }
    }

    fun deleteData(data: Any) {
        list?.let {
            val index = list.indexOf(data)
            list.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updataData(data: Any) {
        list?.let {
            val index = list.indexOf(data)
            list[index] = data
            notifyItemChanged(index)
        }
    }

    fun moveData(fromIndex: Int, toIndex: Int) {
        notifyItemMoved(fromIndex, toIndex)
    }
}