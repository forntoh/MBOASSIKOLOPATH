package com.mboasikolopath.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mboasikolopath.R
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import com.xwray.groupie.Section
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

val Int.inPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.inSp: Float get() = (this * Resources.getSystem().displayMetrics.scaledDensity)

val screenWidth: Int get() = Resources.getSystem().displayMetrics.widthPixels

val screenHeight: Int get() = Resources.getSystem().displayMetrics.heightPixels

fun Activity?.getStatusBarHeight(): Int {
    var result = 0
    val resourceId: Int? = this?.resources?.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId != null) {
        if (resourceId > 0) result = this?.resources?.getDimensionPixelSize(resourceId) ?: 16.inPx
    }
    return result
}

fun Activity.enableWhiteStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.bgLightGrey, null)
    }
}

fun getLoadingDialog(context: Context) = MaterialDialog(context).show {
    customView(view = ProgressBar(context).apply {
        indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
    })
    cancelOnTouchOutside(false)
    this.view.background = ColorDrawable(Color.TRANSPARENT)
}

fun ChipGroup.setupGroup(list: List<String>, titleView: TextView?, listener: View.OnClickListener?) {
    if (list.isNotEmpty()) {
        // Show chip group if it's not empty
        if (titleView != null) titleView.visibility = View.VISIBLE
        this.visibility = View.VISIBLE

        // Add chips to Chip group
        if (this.childCount < 1) {
            list.forEach {
                val chip = Chip(this.context)
                chip.text = it
                chip.isClickable = true
                chip.setOnClickListener(listener)
                this.addView(chip)
            }
        }
    } else {
        // Hide chip group if it's empty
        if (titleView != null) titleView.visibility = View.GONE
        this.visibility = View.GONE
    }
}

fun isFetchNeeded(lastFetchedTime: ZonedDateTime, days: Long = 1): Boolean {
    val fiveMinutesAgo = ZonedDateTime.now().minusDays(days)
    return lastFetchedTime.isBefore(fiveMinutesAgo)
}

fun SimpleSearchView.onQueryTextListener(onQueryTextSubmit: (String) -> Unit) {

    this.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { onQueryTextSubmit.invoke(it) }
            return false
        }

        override fun onQueryTextChange(newText: String?) = false

        override fun onQueryTextCleared(): Boolean = false

    })
}

fun SimpleSearchView.onSearchViewShown(viewShown: () -> Unit, default: () -> Unit) {
    this.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
        override fun onSearchViewShownAnimation() = Unit

        override fun onSearchViewClosed() = if (this@onSearchViewShown.searchEditText.text.isEmpty()) default.invoke()
        else Unit

        override fun onSearchViewClosedAnimation() = Unit

        override fun onSearchViewShown() = viewShown.invoke()
    })
}

fun RecyclerView.toggleViewState(section: Section) {
    if (section.itemCount < 1) this.setState(StatesConstants.EMPTY_STATE)
    else this.setState(StatesConstants.NORMAL_STATE)
}

fun RecyclerView.invalidateViewState() {
    this.setState(StatesConstants.NORMAL_STATE)
    this.setState(StatesConstants.LOADING_STATE)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

@SuppressLint("SimpleDateFormat")
fun Calendar.getDateString(format: String? = "yyyy-MM-dd"): String =
    SimpleDateFormat(format).format(time)

@SuppressLint("SimpleDateFormat")
fun CharSequence?.getLocalDate(format: String? = "yyyy-MM-dd"): LocalDate =
    LocalDate.parse(if (this.isNullOrBlank()) SimpleDateFormat(format).format(Date()) else this, DateTimeFormatter.ofPattern(format, Locale.getDefault()))