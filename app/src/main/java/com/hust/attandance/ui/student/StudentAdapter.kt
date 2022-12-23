package com.hust.attandance.ui.student

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.databinding.ItemStudentBinding
import com.hust.attandance.ui.common.BaseRecyclerViewAdapter
import com.hust.attandance.utils.extensions.setSafeOnClickListener

class StudentAdapter(private val onItemClick: (StudentResponse) -> Unit) :
    BaseRecyclerViewAdapter<StudentResponse, StudentViewHolder>() {
    override fun areItemsSame(oldItem: StudentResponse, newItem: StudentResponse): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsSame(oldItem: StudentResponse, newItem: StudentResponse): Boolean {
        return oldItem == newItem
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            ItemStudentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

}

class StudentViewHolder(
    val viewBinding: ItemStudentBinding,
    onItemClick: (item: StudentResponse) -> Unit
) : BaseRecyclerViewAdapter.BaseViewHolder<StudentResponse>(viewBinding) {
    init {
        viewBinding.btnMore.setSafeOnClickListener {
            item?.let { it1 -> onItemClick.invoke(it1) }
        }
    }

    override fun bind(item: StudentResponse?) {
        super.bind(item)
        item?.let {
            bindInfo(it)
        }
    }

    private fun bindInfo(item: StudentResponse) {
        viewBinding.apply {
            with(item) {
                tvCode.text = code
                tvName.text = name
                tvAddress.text = birthDay
                tvPhoneEmail.text = "$phone - $email"
            }
        }
    }
}