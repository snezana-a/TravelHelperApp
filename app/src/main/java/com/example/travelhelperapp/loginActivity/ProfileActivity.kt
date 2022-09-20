package com.example.travelhelperapp.loginActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.travelhelperapp.R
import com.example.travelhelperapp.model.User
import com.example.travelhelperapp.travelPlacesActivity.TravelPlaceActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var logout: Button
    private lateinit var mAuth: FirebaseAuth

    private lateinit var user: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var userId: String

    private lateinit var email: TextView
    private lateinit var fullName: TextView
    private lateinit var seePlaces: Button
    private lateinit var profilePhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logout = findViewById(R.id.logoutButton)
        mAuth = FirebaseAuth.getInstance()

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginUser::class.java)
            startActivity(intent)
        }

        user = mAuth.currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("users")
        userId = user.uid

        email = findViewById(R.id.emailTitle)
        fullName = findViewById(R.id.fullNameTitle)
        seePlaces = findViewById(R.id.seePlacesButton)
        profilePhoto = findViewById(R.id.profilePhoto)

        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {
            var name: String? = account.displayName
            var mail: String? = account.email

            fullName.text = name
            email.text = mail
        }


        seePlaces.setOnClickListener {
            startActivity(Intent(this, TravelPlaceActivity::class.java))
        }

        reference.child(userId).addListenerForSingleValueEvent (object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile: User? = snapshot.getValue(User::class.java)

                if (userProfile != null) {
                    val fullNameValue: String = userProfile.fullname
                    val emailValue: String = userProfile.email

                    fullName.text = fullNameValue
                    email.text = emailValue
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Something went wrong!", Toast.LENGTH_LONG).show()
            }
        })
    }
}