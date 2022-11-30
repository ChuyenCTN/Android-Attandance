package com.hust.attandance.ui.common

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.OverridingMethodsMustInvokeSuper
import kotlinx.coroutines.*

abstract class BaseRecyclerViewAdapter<T, VH : BaseRecyclerViewAdapter.BaseViewHolder<T>> :
    RecyclerView.Adapter<VH>() {
    private var data: List<T> = listOf()

    private val diffUtil = object : BaseDiffCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return areItemsSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return areContentsSame(oldItem, newItem)
        }

        override fun getChangePayload(oldItem: T, newItem: T): Any? {
            return getPayload(oldItem, newItem)
        }
    }

    abstract fun areItemsSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsSame(oldItem: T, newItem: T): Boolean

    open fun getPayload(oldItem: T, newItem: T): Bundle? {
        return null
    }

    abstract fun createHolder(parent: ViewGroup, viewType: Int): VH

    open fun getViewType(item: T): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return data.size
    }

     override fun getItemViewType(position: Int): Int {
        return getItem(position)?.let { getViewType(it) } ?: 0
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createHolder(parent, viewType)
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    final override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        when (payloads.isNotEmpty() && payloads.first() is Bundle) {
            true -> holder.bind(getItem(position), payloads.first() as Bundle)
            else -> holder.bind(getItem(position))
        }
    }

    private fun getItem(position: Int) = data.getOrNull(position)

    var job: Job? = null

    open suspend fun setData(data: List<T>, onUpdateDispatched: (() -> Unit)? = null) {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }
        coroutineScope {
            job?.cancelAndJoin()
            job = launch {
                val result: DiffUtil.DiffResult
                withContext(Dispatchers.IO + exceptionHandler) {
                    result = diffUtil.apply { setData(this@BaseRecyclerViewAdapter.data, data) }
                        .let { DiffUtil.calculateDiff(it) }
                }
                withContext(Dispatchers.Main + exceptionHandler) {
                    this@BaseRecyclerViewAdapter.data = data
                    result.dispatchUpdatesTo(this@BaseRecyclerViewAdapter)
                    onUpdateDispatched?.invoke()
                }
            }
        }
    }

    fun setData(data: List<T>) {
        this@BaseRecyclerViewAdapter.data = data
    }

    fun getCurrentList() = data

    open class BaseViewHolder<T>(viewBinding: ViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        var item: T? = null

        @OverridingMethodsMustInvokeSuper
        open fun bind(item: T?) {
            try {
                this.item = item
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @OverridingMethodsMustInvokeSuper
        open fun bind(item: T?, payload: Bundle) {
            try {
                this.item = item
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private abstract class BaseDiffCallback<T> : DiffUtil.Callback() {
        private var oldItems: List<T> = emptyList()

        private var newItems: List<T> = emptyList()

        fun setData(oldItems: List<T>, newItems: List<T>) {
            this.oldItems = oldItems
            this.newItems = newItems
        }

        final override fun getOldListSize(): Int {
            return oldItems.size
        }

        final override fun getNewListSize(): Int {
            return newItems.size
        }

        final override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        final override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        final override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return getChangePayload(oldItems[oldItemPosition], newItems[newItemPosition])
        }

        abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

        abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

        open fun getChangePayload(oldItem: T, newItem: T): Any? {
            return null
        }
    }
}