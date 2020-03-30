package com.tricky_tweaks.homekeeping.login.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

        saveLocation.setOnClickListener {
            if (location.text.isEmpty()) {
                location.error = "should not be empty"
                location.requestFocus()
                return@setOnClickListener
            }

            var customerData = CustomerData(
                FirebaseAuth.getInstance().uid!!, FirebaseAuth.getInstance().currentUser!!.phoneNumber,
                location.text.toString(), FirebaseInstanceId.getInstance().getToken()!!
            );

            val documentReference = FirebaseDatabase.getInstance().getReference("Customers")
            documentReference.child(FirebaseAuth.getInstance().uid!!).setValue(customerData)
                .addOnCompleteListener { task: Task<Void?> ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "login successfull", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "network error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@CustomerProfileInfoActivity, ""+it.message, Toast.LENGTH_SHORT).show()
                }

        }
    }
}
