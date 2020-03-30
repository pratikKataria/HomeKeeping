package com.tricky_tweaks.homekeeping.login.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tricky_tweaks.homekeeping.main.MainActivity
import com.tricky_tweaks.homekeeping.R

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



        Handler().postDelayed({
            if (FirebaseAuth.getInstance().uid != null) {
                checkUserData()
            } else {
                sendUserToLogin()
            }
        }, 2000)

    }

    private fun checkUserData() {
        Log.e("OTp activity ", "checkUserData ")
        val ref: DatabaseReference = FirebaseDatabase.getInstance()
            .getReference("Customers/" + FirebaseAuth.getInstance().uid)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    Log.e("OTp activity ", "checkUserData $dataSnapshot")
                } else {
                    startActivity(Intent(this@SplashActivity, CustomerProfileInfoActivity::class.java))
                    Log.e("OTp activity ", "checkUserData $dataSnapshot")
                    finish()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

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
