package com.pabji.myfridge.ui.common.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.Adapter<*>.notifyChanges(
    oldList: List<T>,
    newList: List<T>,
    compare: (T, T) -> Boolean
) {

    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            compare(oldList[oldItemPosition], newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    })
    diff.dispatchUpdatesTo(this)
}

fun SearchView.onTextChange(result: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            result(newText ?: "")
            return false
        }
    })
}

fun <T : View> T?.visible(): T? = this?.apply { visibility = VISIBLE }
fun <T : View> T?.gone(): T? = this?.apply { visibility = GONE }

fun <T : View> T?.setVisible(isVisible: Boolean) = if (isVisible) visible() else gone()
