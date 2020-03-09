package com.mboasikolopath.ui.main.about

import android.annotation.SuppressLint
import android.content.res.TypedArray
import androidx.lifecycle.ViewModel
import com.mboasikolopath.internal.view.SimpleListItem

class AboutViewModel : ViewModel() {

    val about =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

    @SuppressLint("ResourceType")
    fun getSimpleContacts(icons: TypedArray) = listOf(
        SimpleListItem(0, "+237 600000009", 0, icons.getDrawable(0)),
        SimpleListItem(0, "contact@example.com", 0, icons.getDrawable(1)),
        SimpleListItem(0, "www.example.com", 0, icons.getDrawable(2)),
        SimpleListItem(0, "+237 600000009", 0, icons.getDrawable(3))
    )
}
