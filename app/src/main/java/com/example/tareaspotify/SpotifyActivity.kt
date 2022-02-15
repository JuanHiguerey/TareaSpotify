package com.example.tareaspotify

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SpotifyActivity : AppCompatActivity() {
    private var isInit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify)

        val pauseButton = findViewById<ImageButton>(R.id.pauseBtn)
        val playButton = findViewById<ImageButton>(R.id.playBtn)
        val nextButton = findViewById<ImageButton>(R.id.nextBtn)
        val prevButton = findViewById<ImageButton>(R.id.prevBtn)
        val forwardButton = findViewById<ImageButton>(R.id.forwardBtn)
        val rewindButton = findViewById<ImageButton>(R.id.rewindButton)
        val artistTextView = findViewById<TextView>(R.id.artistTextView)
        val trackTitleTextView = findViewById<TextView>(R.id.trackTitleTextView)
        val trackImageView = findViewById<ImageView>(R.id.trackImg)
        val addSongButton = findViewById<ImageView>(R.id.addBtn)

        SpotifyAPI.artistNameWidget = artistTextView
        SpotifyAPI.trackTitleWidget = trackTitleTextView
        SpotifyAPI.trackImageWidget = trackImageView

        pauseButton.setOnClickListener {
            SpotifyAPI.pause()
            pauseButton.visibility = View.GONE
            playButton.visibility = View.VISIBLE
        }

        playButton.setOnClickListener {
            SpotifyAPI.resume()
            playButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
        }

        nextButton.setOnClickListener {
            SpotifyAPI.next()
            playButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
        }

        prevButton.setOnClickListener {
            SpotifyAPI.prev()
            playButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
        }

        forwardButton.setOnClickListener {
            SpotifyAPI.seekPosition(15000)
        }

        rewindButton.setOnClickListener {
            SpotifyAPI.seekPosition(-15000)
        }

        addSongButton.setOnClickListener {
            addSongDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        SpotifyAPI.connect(this)
    }

    override fun onStop() {
        super.onStop()
        SpotifyAPI.disconnect()
    }

    private fun addSongDialog() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Add a Song")

        val input = EditText(this)
        input.hint = "Enter the song's uri to add it to the playlist"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Add") { dialogInterface, i ->
            SpotifyAPI.addSong(input.text.toString())
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialogInterface.cancel()
        }

        builder.show()
    }
}