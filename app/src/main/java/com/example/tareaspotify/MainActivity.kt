package com.example.tareaspotify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val connectButton = findViewById<Button>(R.id.connectBtn)

        connectButton.setOnClickListener {
            val intent = Intent(this, SpotifyActivity::class.java)
            startActivity(intent)
        }
    }
}