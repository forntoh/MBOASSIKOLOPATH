package com.mboasikolopath.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import kotlin.coroutines.CoroutineContext

abstract class ScopedFragment : Fragment(), CoroutineScope, KodeinAware {

    private lateinit var job: Job

    lateinit var auth: FirebaseAuth

    var query: String = ""

    override val kodein by closestKodein()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        auth = FirebaseAuth.getInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
