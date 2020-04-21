package com.tricky_tweaks.homekeeping.login.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.iid.FirebaseInstanceId
import com.tricky_tweaks.homekeeping.main.MainActivity
import com.tricky_tweaks.homekeeping.R
import com.tricky_tweaks.homekeeping.model.CustomerData
import kotlinx.android.synthetic.main.activity_customer_profile_info.*


@Suppress("UNREACHABLE_CODE")
class CustomerProfileInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_profile_info)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                statusBarColor = Color.TRANSPARENT
            }
        }

        saveLocation.setOnClickListener {
            if (location.text!!.isEmpty()) {
                location.error = "should not be empty"
                location.requestFocus()
                return@setOnClickListener
            }

            if (first_name.text!!.isEmpty()) {
                first_name.error = "should not be empty"
                first_name.requestFocus()
                return@setOnClickListener
            }

            if (last_name.text!!.isEmpty()) {
                last_name.error = "should not be empty"
                last_name.requestFocus()
                return@setOnClickListener
            }

            var customerData = CustomerData(
                FirebaseAuth.getInstance().uid!!,
                FirebaseAuth.getInstance().currentUser!!.phoneNumber,
                location.text.toString(),
                FirebaseInstanceId.getInstance().getToken()!!,
                first_name.text.toString().plus(last_name.text.toString())
            );

            progressBar.visibility = View.VISIBLE;

            val documentReference = FirebaseDatabase.getInstance().getReference("Users")
            documentReference.child(FirebaseAuth.getInstance().uid!!).setValue(customerData)
                .addOnSuccessListener {
                    Toast.makeText(this, "login successfull", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE;
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    progressBar.visibility = View.GONE;
                    Toast.makeText(this@CustomerProfileInfoActivity, ""+it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}
