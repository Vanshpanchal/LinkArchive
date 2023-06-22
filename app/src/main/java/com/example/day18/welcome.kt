package com.example.day18

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.day18.databinding.ActivityMainBinding
import com.example.day18.databinding.ActivityWelcomeBinding


class welcome : AppCompatActivity() {

    private lateinit var bind: ActivityWelcomeBinding
    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        bind = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        editor = preferences.edit()
        if (preferences.contains("email") && preferences.contains("pass") && preferences.contains("uid")) {
            intent = Intent(this, home::class.java)
//            val name = preferences.getString("unamepref", "").toString()
//            intent.putExtra("name", name)
            startActivity(intent)
//            finish()
        }

        // Signup
        bind.signUp.setOnClickListener {
            intent = Intent(this, sign_up::class.java)
            startActivity(intent)
            finish()
        }

        // Signin

        bind.signIn.setOnClickListener {
            intent = Intent(this, sign_in::class.java)
            startActivity(intent)
            finish()
        }
    }
}