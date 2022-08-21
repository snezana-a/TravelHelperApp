package com.example.travelhelperapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.travelhelperapp.loginActivity.LoginUser

class MainActivity : AppCompatActivity() {

    private lateinit var iv_plane: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        iv_plane = findViewById(R.id.iv_plane)

        iv_plane.alpha = 0f
        iv_plane.animate().setDuration(2000).alpha(1f).withEndAction {
            val i = Intent(this, LoginUser::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}