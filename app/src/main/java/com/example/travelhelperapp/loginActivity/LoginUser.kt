package com.example.travelhelperapp.loginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.travelhelperapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginUser : AppCompatActivity() {

    private lateinit var register: TextView

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var googleLogin: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBarLogin: ProgressBar
    private lateinit var forgotPassword: TextView

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN: Int = 123;
    private val TAG: String = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        email = findViewById(R.id.emailLogin)
        password = findViewById(R.id.passwordLogin)
        loginButton = findViewById(R.id.loginButton)
        googleLogin = findViewById(R.id.btnGoogle)

        mAuth = Firebase.auth
        progressBarLogin = findViewById(R.id.progressBarLogin)
        forgotPassword = findViewById(R.id.forgotPasswordLink)

        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }

        loginButton.setOnClickListener {
            userLogin()
        }

        register = findViewById(R.id.registerLink)
        register.setOnClickListener {
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

        createRequest()
    }

    override fun onStart() {
        super.onStart()

        val user: FirebaseUser? = mAuth.currentUser

        if (user != null) {
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createRequest() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        googleLogin.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                signIn()
            }
        })
    }

    private fun signIn() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account : GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e : ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)

        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = mAuth.currentUser
                        val intent = Intent(applicationContext, ProfileActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginUser, "Sorry auth failed", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    private fun userLogin() {
        val emailValue: String = email.text.toString().trim()
        val passwordValue: String = password.text.toString().trim()

        if (emailValue.isEmpty()) {
            email.error = "E-mail is required!"
            email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            email.error = "Please enter a valid e-mail address!"
            email.requestFocus()
            return
        }

        if (passwordValue.isEmpty()) {
            password.error = "Password is required!"
            password.requestFocus()
            return
        }

        if (passwordValue.length < 6) {
            password.error = "Min password length is 6 characters!"
            password.requestFocus()
            return
        }

        progressBarLogin.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

                    if (user?.isEmailVerified == true) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    } else {
                        user?.sendEmailVerification()
                        Toast.makeText(this, "Check your e-mail to verify your account!", Toast.LENGTH_LONG).show()
                        progressBarLogin.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this, "Failed to login! Please check your credentials!", Toast.LENGTH_LONG).show()
                }

            }
    }
}