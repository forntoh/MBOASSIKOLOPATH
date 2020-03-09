package com.mboasikolopath.ui.login

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.afollestad.materialdialogs.list.updateListItemsSingleChoice
import com.github.paolorotolo.appintro.AppIntro2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mboasikolopath.R
import com.mboasikolopath.data.model.*
import com.mboasikolopath.ui.main.MainActivity
import com.mboasikolopath.utilities.*
import kotlinx.android.synthetic.main.fragment_setup_name.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Job
import lib.kingja.switchbutton.SwitchMultiButton
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

class SetupActivity : AppIntro2(), KodeinAware, CoroutineScope {

    override val kodein by closestKodein()

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val firebaseUser: FirebaseUser? by lazy { FirebaseAuth.getInstance().currentUser }

    private val user by lazy {
        var u = User(1, "", "")
        runBlocking {
            u = if (firebaseUser != null)
                User(1, firebaseUser!!.email!!, firebaseUser!!.displayName ?: "", firebaseUser!!.uid)
            else {
                val a = viewModel.getUserAsync() ?: User(1, "", "")
                startActivity(Intent(this@SetupActivity, LoginActivity::class.java))
                finish()
                a
            }
        }
        return@lazy u
    }

    private lateinit var viewModel: SetupViewModel
    private val viewModelFactory: SetupViewModelFactory by instance()

    private val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

    private val summary by lazy {
        TextView(this).apply {
            layoutParams = lp
            textSize = 10.inSp
            setShadowLayer(2f, 1f, 1f, 0xffffff)
        }
    }
    private val primaryColor by lazy { ContextCompat.getColor(this, R.color.colorPrimary) }
    private var localites: List<Localite> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        viewModel = ViewModelProvider(this, viewModelFactory).get(SetupViewModel::class.java)
        enableWhiteStatusBar()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        bindUI()
    }

    private fun bindUI() {
        addSlide(
            SetupNameFragment.newInstance(
                "How should we call you?",
                with(TextInputLayout(this)) {
                    layoutParams = lp
                    addView(TextInputEditText(this@SetupActivity).apply {
                        inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                        hint = "Your name"

                        if (firebaseUser != null) {
                            this.isEnabled = false
                            setText(firebaseUser!!.displayName)
                        }

                        afterTextChanged {
                            if (it.length < 6) this@with.error = "Must be more than 6 characters"
                            else {
                                this@with.error = ""
                                user.Name = it.trim()
                            }
                        }
                    }, lp)
                    this
                })
        )

        addSlide(
            SetupNameFragment.newInstance(
                "What is your gender?",
                LayoutInflater.from(this).inflate(
                    R.layout.fragment_setup_personal_info,
                    container,
                    false
                ).apply {
                    if (this is SwitchMultiButton) {
                        setOnSwitchListener { position, _ ->
                            when (position) {
                                0 -> user.Gender = "M"
                                else -> user.Gender = "F"
                            }
                        }
                    }
                    layoutParams = lp
                })
        )

        addSlide(
            SetupNameFragment.newInstance(
                "When were you born?",
                FloatingActionButton(this).apply {
                    setImageResource(R.drawable.ic_date)
                    setBackgroundColor(primaryColor)
                    customSize = 92.inPx
                    scaleType = ImageView.ScaleType.CENTER

                    setOnClickListener {
                        MaterialDialog(context!!).show {
                            title(null, "Date of birth")
                            datePicker(maxDate = Calendar.getInstance().apply {
                                set(Calendar.YEAR, get(Calendar.YEAR) - 10)
                            }) { _, date ->
                                user.Dob = date.getDateString()
                                pager.goToNextSlide()
                            }
                            lifecycleOwner(this@SetupActivity)
                        }
                    }

                    layoutParams = lp.apply { gravity = Gravity.CENTER; setMargins(8.inPx) }
                })
        )

        var regions: List<Region>
        var departements: List<Departement> = emptyList()
        var arrondissements: List<Arrondissement> = emptyList()
        addSlide(
            SetupNameFragment.newInstance(
                "Where are you located?",
                FloatingActionButton(this).apply {
                    setImageResource(R.drawable.ic_location_on_black_24dp)
                    setBackgroundColor(primaryColor)
                    customSize = 92.inPx
                    scaleType = ImageView.ScaleType.CENTER

                    setOnClickListener {

                        launch {
                            regions = viewModel.regions.await()

                            var level = 0

                            MaterialDialog(context!!).show {
                                title(null, "Region")
                                listItemsSingleChoice(items = regions.map { it.Name }) { dialog, index, _ ->
                                    // Invoked when the user selects an item
                                    level++
                                    runBlocking {

                                        when (level) {
                                            1 -> {
                                                departements = viewModel.findDepartementsOfRegion(regions[index].RegionID)
                                                dialog.updateListItemsSingleChoice(items = departements.map { it.Name })
                                                title(null, "Departements")
                                            }
                                            2 -> {
                                                arrondissements = viewModel.findArrondissementsOfDepartement(departements[index].DepartementID)
                                                dialog.updateListItemsSingleChoice(items = arrondissements.map { it.Name })
                                                title(null, "Arrondissements")
                                            }
                                            3 -> {
                                                localites = viewModel.findLocalitesOfArrondissement(arrondissements[index].ArrondissementID)
                                                dialog.updateListItemsSingleChoice(items = localites.map { it.Name })
                                                title(null, "Localites")
                                            }
                                            else -> {
                                                user.LocaliteID = localites[index].LocaliteID
                                                dialog.dismiss()
                                                pager.goToNextSlide()
                                            }
                                        }
                                    }
                                }
                                noAutoDismiss()
                                lifecycleOwner(this@SetupActivity)
                            }
                        }
                    }

                    layoutParams = lp.apply {
                        gravity = Gravity.CENTER
                        setMargins(16.inPx)
                    }
                })
        )

        addSlide(SetupNameFragment.newInstance("Is this okay?", summary))

        selectedIndicatorColor = ContextCompat.getColor(this, R.color.colorPrimary)
        unselectedIndicatorColor = ContextCompat.getColor(this, R.color.colorPrimaryLight)


        val nextDrawable =
            ContextCompat.getDrawable(this, R.drawable.ic_appintro_navigate_next_white)!!.apply {
                mutate().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN)
            }
        val backDrawable =
            ContextCompat.getDrawable(this, R.drawable.ic_appintro_navigate_before_white)!!.apply {
                mutate().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN)
            }
        val doneDrawable =
            ContextCompat.getDrawable(this, R.drawable.ic_appintro_done_white)!!.apply {
                mutate().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN)
            }

        (nextButton as ImageButton).setImageDrawable(nextDrawable)
        (backButton as ImageButton).setImageDrawable(backDrawable)
        (doneButton as ImageButton).setImageDrawable(doneDrawable)

        backgroundFrame.setBackgroundColor(Color.WHITE)

        showSkipButton(false)
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        try {
            summary.text = Html.fromHtml(
                "Hi, my name is <b>${user.Name}</b>, I am a <b>${if (user.Gender == "M") "boy" else "girl"}</b>, I was born on <b>${user.Dob}</b> which makes me <b>${
                abs(ChronoUnit.YEARS.between(LocalDate.now(), user.Dob!!.getLocalDate()))
                }yrs</b>, I live in <b>${localites.find { it.LocaliteID == user.LocaliteID }?.Name}</b>."
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        if (user.Name.isBlank() || user.Dob.isNullOrBlank() || user.LocaliteID < 0)
            Toast.makeText(this, "Please review the information you provided", Toast.LENGTH_LONG).show()
        else launch {
            val loading = getLoadingDialog(this@SetupActivity)
            viewModel.signup(user)
            viewModel.getUser().observe(this@SetupActivity, androidx.lifecycle.Observer {
                if (it != null && !it.Token.isNullOrBlank()) {
                    loading.dismiss()
                    launchMainActivity()
                } else {
                    loading.dismiss()
                    Toast.makeText(this@SetupActivity, it?.error, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        launch { viewModel.downloadLocations() }
        if (!user.Token.isNullOrBlank()) launchMainActivity()
    }

    private fun launchMainActivity() {
        startActivity(Intent(this@SetupActivity, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
