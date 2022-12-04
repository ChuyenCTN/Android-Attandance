package com.hust.attandance.ui.common.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.hust.attandance.R
import com.hust.attandance.utils.extensions.setTextAppearanceSystem
import com.hust.attandance.utils.extensions.toggleVisibility

class CustomBadgeTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    private val customViews = mutableListOf<View>()

    fun inflateTabView(title: String): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        tvTitle.text = title
        customViews.add(view)
        return view
    }

    fun updateBadge(position: Int, count: Int) {
        if (customViews.isEmpty()) return
        val view = customViews[position]
        val tvBadge: TextView = view.findViewById(R.id.tvBadge)
        tvBadge.toggleVisibility(count > 0)
        tvBadge.text = if (count > 99) "99+" else count.toString()
    }

    fun updateTabSelected(position: Int) {
        if (customViews.isEmpty()) return
        customViews.forEachIndexed { index, view ->
            val tvTitle: TextView = view.findViewById(R.id.tvTitle)
            tvTitle.setTextAppearanceSystem(
                if (index == position) {
                    R.style.TextAppearance_Body1
                } else {
                    R.style.TextAppearance_Body2
                }
            )
            tvTitle.setTextColor(
                if (index == position) {
                    ContextCompat.getColor(context, R.color.primary_text_dark)
                } else {
                    ContextCompat.getColor(context, R.color.primary_dark)
                }
            )
        }

    }
}