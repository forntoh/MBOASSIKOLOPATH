package com.mboasikolopath.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.mboasikolopath.R
import com.mboasikolopath.data.model.User
import com.mboasikolopath.ui.main.MainActivity
import com.mboasikolopath.utilities.enableWhiteStatusBar
import com.mboasikolopath.utilities.getLoadingDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), OnCompleteListener<AuthResult>, KodeinAware,
    CoroutineScope {

    override val kodein by closestKodein()

    private lateinit var auth: FirebaseAuth

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // Initialize Facebook Login button
    private val callbackManager = CallbackManager.Factory.create()
    // Configure Google Sign In
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var viewModel: SetupViewModel
    private val viewModelFactory: SetupViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        job = Job()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SetupViewModel::class.java)
        bindUI()
    }

    private fun bindUI() {
        enableWhiteStatusBar()
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() = Unit
                override fun onError(error: FacebookException?) = Unit
            })

        // Configure Google Sign In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btn_fb_login.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("email", "public_profile"))
        }
        btn_g_login.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener(this, this)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this, this)
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in loginAsync's information
            val user = auth.currentUser
            updateUI(user)
        } else {
            // If sign in fails, display a message to the loginAsync.
            Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
            updateUI(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                e.printStackTrace()
            }
        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        //TODO: Change behavior
        if (user == null) return

        val userAsync = viewModel.getUserAsync()

        if (userAsync != null) {
            if (userAsync.Token.isNullOrBlank()) tryToLogin(user)
            else launchMainActivity()
        } else tryToLogin(user)
    }

    private fun tryToLogin(user: FirebaseUser) = launch {
        val loading = getLoadingDialog(this@LoginActivity)
        viewModel.login(User(1, user.email!!, user.displayName ?: "", user.uid))
        viewModel.getUser().observe(this@LoginActivity, androidx.lifecycle.Observer {
            loading.dismiss()
            if (it != null && !it.Token.isNullOrBlank()) launchMainActivity() else launchSetupActivity()
        })
    }

    private fun launchSetupActivity() {
        startActivity(Intent(this, SetupActivity::class.java))
        finish()
    }

    private fun launchMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onResume() {
        super.onResume()
        launch { viewModel.downloadLocations() }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        private const val RC_SIGN_IN = 100
    }

}
