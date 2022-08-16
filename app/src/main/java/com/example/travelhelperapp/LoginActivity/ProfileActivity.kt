package com.example.travelhelperapp.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.travelhelperapp.R
import com.example.travelhelperapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var logout: Button
    private lateinit var mAuth: FirebaseAuth

    private lateinit var user: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var userId: String

    private lateinit var greeting: TextView
    private lateinit var email: TextView
    private lateinit var fullName: TextView
    private lateinit var age: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logout = findViewById(R.id.logoutButton)
        mAuth = FirebaseAuth.getInstance()

        logout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, LoginUser::class.java))
        }

        user = mAuth.currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("users")
        userId = user.uid

        email = findViewById(R.id.emailTitle)
        fullName = findViewById(R.id.fullNameTitle)
        age = findViewById(R.id.ageTitle)
        greeting = findViewById(R.id.greetingTitle)

        reference.child(userId).addListenerForSingleValueEvent (object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile: User? = snapshot.getValue(User::class.java)

                if (userProfile != null) {
                    val fullNameValue: String = userProfile.fullname
                    val emailValue: String = userProfile.email
                    val ageValue: String = userProfile.age

                    greeting.text = "Welcome, $fullNameValue!"
                    fullName.text = fullNameValue
                    email.text = emailValue
                    age.text = ageValue
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Something went wrong!", Toast.LENGTH_LONG).show()

            }
        })
    }
}