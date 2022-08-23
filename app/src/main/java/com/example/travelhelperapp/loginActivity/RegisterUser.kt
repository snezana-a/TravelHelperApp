package com.example.travelhelperapp.loginActivity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.travelhelperapp.R
import com.example.travelhelperapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterUser : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var fullName: EditText
    private lateinit var age: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerButton: Button
    private lateinit var progressBarRegister: ProgressBar
    private lateinit var alreadyHaveAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        mAuth = Firebase.auth

        fullName = findViewById(R.id.fullNameRegister)
        email = findViewById(R.id.emailRegister)
        password = findViewById(R.id.passwordRegister)

        registerButton = findViewById(R.id.registerButton)
        progressBarRegister = findViewById(R.id.progressBarRegister)
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount)

        alreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this, LoginUser::class.java))
        }

        registerButton.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {

        val emailValue: String = email.text.toString().trim()
        val fullNameValue: String = fullName.text.toString().trim()
        val ageValue: String = age.text.toString().trim()
        val passwordValue: String = password.text.toString().trim()

        if (fullNameValue.isEmpty()) {
            fullName.error = "Full name is required!"
            fullName.requestFocus()
            return
        }

        if (emailValue.isEmpty()) {
            email.error = "E-mail is required!"
            email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            email.error = "Please provide a valid e-mail address!"
            email.requestFocus()
            return
        }

        if (passwordValue.isEmpty()) {
            password.error = "Password is required!"
            password.requestFocus()
            return
        }

        if(passwordValue.length < 6) {
            password.error = "Min password length should be 6 characters!"
            password.requestFocus()
            return
        }

        progressBarRegister.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = User(fullNameValue, ageValue, emailValue)

                    FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .setValue(user).addOnCompleteListener { it1 ->
                            if (it1.isSuccessful) {
                                Toast.makeText(this, "User has been registered successfully!", Toast.LENGTH_LONG).show()
                                progressBarRegister.visibility = View.GONE
                                startActivity(Intent(this, LoginUser::class.java))
                            } else {
                                Toast.makeText(this, "Failed to register. Please try again!", Toast.LENGTH_LONG).show()
                                progressBarRegister.visibility = View.GONE
                            }
                        }
                } else {
                    Toast.makeText(this, "Failed to register. Please try again!", Toast.LENGTH_LONG).show()
                    progressBarRegister.visibility = View.GONE
                }
            }
    }

}