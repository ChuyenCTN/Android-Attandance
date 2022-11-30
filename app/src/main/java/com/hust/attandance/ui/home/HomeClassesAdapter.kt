package com.hust.attandance.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hust.attandance.data.model.ClassResponse
import com.hust.attandance.databinding.ItemClassesBinding
import com.hust.attandance.ui.common.BaseRecyclerViewAdapter

class HomeClassesAdapter(val onClick: (item: ClassResponse) -> Unit) :
    BaseRecyclerViewAdapter<ClassResponse, ClassesViewHolder>() {
    override fun areItemsSame(oldItem: ClassResponse, newItem: ClassResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsSame(oldItem: ClassResponse, newItem: ClassResponse): Boolean {
        return oldItem == newItem
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): ClassesViewHolder {
        return ClassesViewHolder(
            ItemClassesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick
        )
    }
}