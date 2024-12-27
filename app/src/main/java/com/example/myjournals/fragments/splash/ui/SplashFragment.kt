package com.example.myjournals.fragments.splash.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myjournals.R
import com.example.myjournals.databinding.FragmentSplashBinding
import com.example.myjournals.fragments.splash.viewModel.SplashViewModel


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val splashViewModel: SplashViewModel by viewModels()
    private var splashTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSplashTimer()

    }


    // Initializes the splash screen timer
    private fun initSplashTimer() {
        // Ensure the activity is not null
        activity?.let { fragmentActivity ->
            with(binding) {
                // Show the progress bar and set it up
                progressBar.show()
                progressBar.max = 3000
                progressBar.progress = 0

                // Cancel any existing timer
                splashTimer?.cancel()

                // Create and start a new timer for 3 seconds
                splashTimer = object : CountDownTimer(3000, 100) {
                    override fun onTick(millisUntilFinished: Long) {
                        val progress = (3000 - millisUntilFinished).toInt()
                        val percentage = (progress * 100 / 3000).toString()
                        // Update progress bar and text
                        if (isVisible && !isDetached) {
                            progressBar.progress = progress
//                            start.text = "${percentage}%"
                        }
                    }

                    override fun onFinish() {
                        // Set progress to 100% and navigate to the next screen
//                        start.text = "100%"
                        moveToNext()
                        splashTimer?.cancel()
                    }
                }.apply {
                    start()
                }
            }
        }
    }


    private fun moveToNext(){
        findNavController().navigate(R.id.homeFragment)
    }

    private fun cancelTimer() {
        with(binding) {
            splashViewModel.remainingProgress = 0
            progressBar.progress = progressBar.max
            splashTimer?.onFinish()
            splashTimer?.cancel()
        }
    }

}