package com.hust.attandance.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hust.attandance.data.model.ScheduleResponse
import com.hust.attandance.databinding.ItemAttandanceBinding
import com.hust.attandance.ui.common.BaseRecyclerViewAdapter
import com.hust.attandance.utils.extensions.setSafeOnClickListener

class SchedulesAdapter(private val onItemClick: (ScheduleResponse) -> Unit) :
    BaseRecyclerViewAdapter<ScheduleResponse, ScheduleViewHolder>() {
    override fun areItemsSame(oldItem: ScheduleResponse, newItem: ScheduleResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsSame(oldItem: ScheduleResponse, newItem: ScheduleResponse): Boolean {
        return oldItem == newItem
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            ItemAttandanceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

}

class ScheduleViewHolder(
    val viewBinding: ItemAttandanceBinding,
    onItemClick: (item: ScheduleResponse) -> Unit
) : BaseRecyclerViewAdapter.BaseViewHolder<ScheduleResponse>(viewBinding) {
    init {
        viewBinding.btnMore.setSafeOnClickListener {
            item?.let { it1 -> onItemClick.invoke(it1) }
        }
    }

    override fun bind(item: ScheduleResponse?) {
        super.bind(item)
        item?.let {
            bindInfo(it)
        }
    }

    private fun bindInfo(item: ScheduleResponse) {
        viewBinding.apply {
            with(item) {
                tvDate.text = "Ngày: $date"
                tvTime.text = "Ca: $session - $time"
                tvCount.text = "Sĩ số $number/$total"
                tvNote.text = "$note"
            }
        }
    }
}