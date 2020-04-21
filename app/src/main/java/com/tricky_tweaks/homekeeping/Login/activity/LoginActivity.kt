package com.tricky_tweaks.homekeeping.login.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.tricky_tweaks.homekeeping.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private lateinit var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var phoneNumber : TextInputEditText
    private lateinit var countryCode : EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var countDownTimer: TextView
    private lateinit var sendOtpButton : MaterialButton

    private fun initFields() {
        progressBar = findViewById(R.id.activity_login_pb_counter)
        countryCode = findViewById(R.id.activity_login_et_country_code)
        sendOtpButton = findViewById(R.id.activity_login_mb_send_otp)
        phoneNumber = findViewById(R.id.activity_login_et_phone_number)
        countDownTimer = findViewById(R.id.activity_login_tv_count_down_time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initFields()

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownTimer.text = "" + millisUntilFinished / 1000
            }

            override fun onFinish() {
                countDownTimer.text = ""
                progressBar.visibility = GONE
                sendOtpButton.isEnabled = true
            }
        }

        sendOtpButton.setOnClickListener {

            if (phoneNumber.text!!.isEmpty()) {
                phoneNumber.error = "should not be empty"
                phoneNumber.requestFocus()
                return@setOnClickListener
            }

            if (phoneNumber.text!!.length < 10) {
                phoneNumber.error = "invalid number"
                phoneNumber.requestFocus()
                return@setOnClickListener
            }

            progressBar.visibility = VISIBLE
            countDownTimer.visibility = VISIBLE

            timer.cancel()
            val completePhoneNumber = countryCode.text.toString() + phoneNumber.text.toString()

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                completePhoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBacks
            )

            timeCounter()
        }



        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.


//                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
//                Log.w(TAG, "onVerificationFailed", e)

              Toast.makeText(this@LoginActivity, "error:" + e.message, Toast.LENGTH_SHORT);

                when (e) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        // Invalid request
                        // ...
                        activity_login_tv_error.text = e.message;
                    }
                    is FirebaseTooManyRequestsException -> {
                        // The SMS quota for the project has been exceeded
                        // ...
                        activity_login_tv_error.text = e.message;
                    }
                    else -> {
                        activity_login_tv_error.text = e.message;

                    }
                }
                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
//                storedVerificationId = verificationId
//                resendToken = token

                sendOtpButton.isEnabled = false
                Handler().postDelayed(
                    {
                        val otpIntent = Intent(this@LoginActivity, OtpActivity::class.java)
                        otpIntent.putExtra("AuthCredentials", verificationId)
                        startActivity(otpIntent)

                    }, 2000
                )

                Toast.makeText(this@LoginActivity, "otp sent", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun timeCounter() {
        timer.start()
    }

}
