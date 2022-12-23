package com.hust.attandance.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.databinding.ItemStudentDetectBinding
import com.hust.attandance.ui.common.BaseRecyclerViewAdapter
import com.hust.attandance.utils.extensions.toggleVisibility

class StudentsDetectAdapter(private val onItemClick: (StudentResponse) -> Unit) :
    BaseRecyclerViewAdapter<StudentResponse, StudentDetectViewHolder>() {
    override fun areItemsSame(oldItem: StudentResponse, newItem: StudentResponse): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsSame(oldItem: StudentResponse, newItem: StudentResponse): Boolean {
        return oldItem.checked == newItem.checked && oldItem == newItem
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): StudentDetectViewHolder {
        return StudentDetectViewHolder(
            ItemStudentDetectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

}

class StudentDetectViewHolder(
    val viewBinding: ItemStudentDetectBinding,
    onItemClick: (item: StudentResponse) -> Unit
) : BaseRecyclerViewAdapter.BaseViewHolder<StudentResponse>(viewBinding) {
    init {

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
                tvName.text = "$name \n $birthDay"
                btnChecked.toggleVisibility(checked)
            }
        }
    }
}