package com.example.myjournals.activites
//
//import android.os.SystemClock
//import androidx.activity.OnBackPressedCallback
//
//class BaseFragment {
//    private lateinit var backPressedCallback: OnBackPressedCallback
//    var lastClickTime: Long = 0
//
//    fun configureBackPress(backPressed: () -> Unit) {
//
//        backPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (SystemClock.elapsedRealtime() - lastClickTime < 600) return
//                else {
//                    //postAnalytic(analytics)
//                    backPressed()
//                }
//                lastClickTime = SystemClock.elapsedRealtime()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
//    }
//
//    fun removeBackPress() {
//        if (this::backPressedCallback.isInitialized) {
//            backPressedCallback.isEnabled = false
//            backPressedCallback.remove()
//        }
//    }
//}