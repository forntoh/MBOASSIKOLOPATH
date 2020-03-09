package com.mboasikolopath.internal.view

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mboasikolopath.R
import com.mboasikolopath.utilities.inSp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

data class SimpleListItem(var _id: Int, var text: String, var icon: Int = 0, var drawable: Drawable? = null) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        (viewHolder.itemView as TextView).text = text

        if (drawable != null) {
            with(viewHolder.itemView as TextView) {
                setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                textSize = 6.inSp
                maxLines = 2
            }
            return
        }

        var drawableIcon = ContextCompat.getDrawable(viewHolder.itemView.context!!, R.drawable.ic_book_open_page)
        if (icon == 1) drawableIcon = ContextCompat.getDrawable(viewHolder.itemView.context!!, R.drawable.ic_worker)

        (viewHolder.itemView as TextView).setCompoundDrawablesWithIntrinsicBounds(
            drawableIcon.apply {
                this!!.setColorFilter(
                    viewHolder.itemView.context.resources.getColor(R.color.colorPrimary),
                    PorterDuff.Mode.SRC_ATOP
                )
            }, null, null, null
        )
    }

    override fun getLayout() = R.layout.item_list_simple
}
