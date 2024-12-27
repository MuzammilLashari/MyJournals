package com.example.myjournals.fragments.splash.viewModel

import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {

    val timerCountInterval = 1L // timer count interval between updates to the timer interval (in milliseconds)
    var maxProgress = 16000L // max progress of the splash screen (in milliseconds)
    var remainingProgress = 16000L // remaining progress of the splash screen after a certain amount of time (in milliseconds)

    /**
     * Returns the current progress of the splash screen
     */
    fun currentProgress() = maxProgress - remainingProgress

    /**
     * Returns the current progress percentage of the splash screen
     */
    fun progressPercentage() = (currentProgress() * 100) / maxProgress

    var comeFromPauseState = false

}