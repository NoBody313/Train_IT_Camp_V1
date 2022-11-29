package com.example.trainitcampv1.presentation.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.trainitcampv1.MainActivity

//@SuppressLint("CustomSplashScreen")
//class SplashScreenActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            val content: View = findViewById(android.R.id.content)
//            content.viewTreeObserver.addOnPreDrawListener(
//                object : ViewTreeObserver.OnPreDrawListener {
//                    override fun onPreDraw(): Boolean {
//
//                        return run {
//                            content.viewTreeObserver.removeOnPreDrawListener(this)
//                            true
//                        }
//                    }
//                }
//            )
//        } else {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
//}

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}