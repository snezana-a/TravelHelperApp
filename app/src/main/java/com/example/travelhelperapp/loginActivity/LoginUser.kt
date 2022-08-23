package com.example.travelhelperapp.loginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.travelhelperapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginUser : AppCompatActivity() {

    private lateinit var register: TextView

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBarLogin: ProgressBar
    private lateinit var forgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        email = findViewById(R.id.emailLogin)
        password = findViewById(R.id.passwordLogin)
        loginButton = findViewById(R.id.loginButton)

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