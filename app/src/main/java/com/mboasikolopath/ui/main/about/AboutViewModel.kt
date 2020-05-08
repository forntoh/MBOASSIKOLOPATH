package com.mboasikolopath.ui.main.about

import android.annotation.SuppressLint
import android.content.res.TypedArray
import androidx.lifecycle.ViewModel
import com.mboasikolopath.internal.view.SimpleListItem

class AboutViewModel : ViewModel() {

    @SuppressLint("ResourceType")
    fun getSimpleContacts(icons: TypedArray) = listOf(
        SimpleListItem(0, "+237 6 91 52 24 18", 0, icons.getDrawable(0)),
        SimpleListItem(0, "contact@mboasikulu.com", 0, icons.getDrawable(1)),
        SimpleListItem(0, "http://www.mboasikulu.com/", 0, icons.getDrawable(2)),
        SimpleListItem(0, "MBOMDA Joseph", 0, icons.getDrawable(3))
    )
}
