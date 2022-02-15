package com.example.tareaspotify

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

@SuppressLint("StaticFieldLeak")
object SpotifyAPI {
    private const val clientId = "725fff6ba6204a288ff8db5f34b12889"
    private const val redirectUri = "https://www.tec.ac.cr/"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    private var songs = mutableListOf(
        "spotify:track:1Xi84slp6FryDSCbzq4UCD",
        "spotify:track:75JFxkI2RXiU7L9VXzMkle",
        "spotify:track:0v1XpBHnsbkCn7iJ9Ucr1l",
    )
    private var songIndex = 0
    lateinit var artistNameWidget : TextView
    lateinit var trackTitleWidget : TextView
    lateinit var trackImageWidget : ImageView

    fun connect(context: Context) {
        Log.d("SpotifyAPI", "Trying to connect")
        if (spotifyAppRemote?.isConnected == true) {
            return
        }
        val connectionParams: ConnectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()
        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                this@SpotifyAPI.spotifyAppRemote = spotifyAppRemote
                play()
                connected()
            }
            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyAPI", throwable.message, throwable)
            }
        }
        SpotifyAppRemote.connect(context, connectionParams, connectionListener)
    }

    fun disconnect() {
        pause()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

    fun pause() {
        spotifyAppRemote?.let {
            it.playerApi.pause()
        }
    }

    fun resume() {
        spotifyAppRemote?.let {
            it.playerApi.resume()
        }
    }

    fun next() {
        if(songIndex == songs.size - 1) {
            songIndex = 0
        }
        else {
            songIndex++
        }
        play()
    }

    fun prev() {
        if(songIndex == 0) {
            songIndex = songs.size - 1
        }
        else {
            songIndex--
        }
        play()
    }

    fun seekPosition(millis : Long) {
        spotifyAppRemote?.let {
            it.playerApi.seekToRelativePosition(millis)
        }
    }

    fun addSong(uri: String) {
        songs.add("spotify:track:" + uri.substring(31, 53))
    }

    private fun play() {
        spotifyAppRemote?.let {
            it.playerApi.play(songs[songIndex])
        }
    }

    private fun connected() {
        spotifyAppRemote?.let { appRemote ->
            appRemote.playerApi.subscribeToPlayerState().setEventCallback { playerState ->
                artistNameWidget.text = playerState.track.artist.name
                trackTitleWidget.text = playerState.track.name
                appRemote.imagesApi.getImage(playerState.track.imageUri).setResultCallback {
                    trackImageWidget.setImageBitmap(it)
                }
            }
        }
    }
}
