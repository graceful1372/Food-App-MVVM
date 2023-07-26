package com.graceful1372.foodappnouri.ui.detail

import android.os.Bundle
import android.view.WindowManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.graceful1372.foodappnouri.databinding.ActivityPlayerrBinding
import com.graceful1372.foodappnouri.utlis.VIDEO_ID
import com.graceful1372.foodappnouri.utlis.YOUTUBE_API_KEY
import androidx.core.view.WindowCompat


@Suppress("DEPRECATION")
class PlayerActivity : YouTubeBaseActivity() {

    private  var _binding: ActivityPlayerrBinding? = null
    private val binding get() = _binding!!

    //Other
    private lateinit var player :YouTubePlayer
    private var videoId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        _binding = ActivityPlayerrBinding.inflate(layoutInflater)

        //Full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)

        //Get id
        videoId = intent.getSerializableExtra(VIDEO_ID).toString()
        //Player initialize
        val listener = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer,
                p2: Boolean
            ) {
                player = p1
                player.loadVideo(videoId)
                player.play()
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
            }
        }

        //InitViews
        binding.videoPlayer.initialize(YOUTUBE_API_KEY, listener)




    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }


}