package com.hust.attandance.ui.home

import com.hust.attandance.data.model.ClassResponse
import com.hust.attandance.databinding.ItemClassesBinding
import com.hust.attandance.ui.common.BaseRecyclerViewAdapter
import com.hust.attandance.utils.extensions.setSafeOnClickListener

class ClassesViewHolder(
    val viewBinding: ItemClassesBinding, onClick: (item: ClassResponse) -> Unit
) : BaseRecyclerViewAdapter.BaseViewHolder<ClassResponse>(viewBinding) {
    init {
        viewBinding.root.setSafeOnClickListener {
            item?.let {
                onClick.invoke(it)
            }
        }
    }

    private fun bindData(item: ClassResponse) {
        viewBinding.apply {
            tvTitle.text = item.name ?: ""
            tvDescription.text = item.description ?: ""
            tvCountNumber.text = "Sĩ số: ${item.studentIds.size}"
        }
    }

    override fun bind(item: ClassResponse?) {
        super.bind(item)
        item?.let { bindData(it) }
    }
}