package com.example.travelhelperapp.loginActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.travelhelperapp.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var reset: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        email = findViewById(R.id.resetEmail)
        reset = findViewById(R.id.resetPasswordButton)
        progressBar = findViewById(R.id.progressBarResetPassword)
        mAuth = FirebaseAuth.getInstance()

        reset.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val emailValue: String = email.text.toString().trim()

        if (emailValue.isEmpty()) {
            email.error = "E-mail is required!"
            email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            email.error = "Please provide a valid e-mail address!"
            email.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        mAuth.sendPasswordResetEmail(emailValue).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Check your e-mail to reset your password!", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            } else {
                Toast.makeText(this, "Try again! Something went wrong!", Toast.LENGTH_LONG).show()
            }
        }
    }
}