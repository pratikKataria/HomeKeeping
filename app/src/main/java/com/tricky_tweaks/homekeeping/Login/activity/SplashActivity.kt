package com.tricky_tweaks.homekeeping.login.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tricky_tweaks.homekeeping.R
import com.tricky_tweaks.homekeeping.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private var logo: ImageView? = null
    private var linearLayout: LinearLayout? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        logo = findViewById(R.id.transition_ic_logo)
        linearLayout = findViewById(R.id.linearLayout)
        textView = findViewById(R.id.transition_text)

//        startActivity(Intent(this@SplashActivity, MainActivity::class.java))

            if (FirebaseAuth.getInstance().uid != null) {
                checkUserData()
            } else {
                sendUserToLogin()
            }

    }

    private fun checkUserData() {
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().uid)
        ref.keepSynced(true)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Handler().postDelayed( {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }, 1500)
                } else {
                    Handler().postDelayed( {
                        startActivity(Intent(this@SplashActivity, CustomerProfileInfoActivity::class.java))
                        finish()
                    }, 1000)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    //shared element animation
    fun sendUserToLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        val pairs: Array<Pair<View, String>> =
            arrayOf(
                Pair<View, String>(logo, "logoTrans"),
                Pair<View, String>(linearLayout, "llTrans"),
                Pair<View, String>(textView, "descTrans")
            )
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            *pairs
        )

        startActivity(intent)
        finish()
    }

}
