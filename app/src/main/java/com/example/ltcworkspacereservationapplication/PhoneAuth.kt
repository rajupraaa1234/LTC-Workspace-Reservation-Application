package com.example.ltcworkspacereservationapplication

import android.content.Context
import android.util.Log
import com.example.ltcworkspacereservationapplication.presentation.utils.FirebaseUtils
import com.example.ltcworkspacereservationapplication.presentation.utils.getActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

var storedVerificationId: String? = null

fun PhoneAuth(context: Context, phoneNumber: String, onCodeSent: () -> Unit) {
    val auth = FirebaseUtils.firebaseAuth

    auth.setLanguageCode("en")
    val callback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("phoneBook","verification completed")
            signInWithPhoneAuthCredential(context, credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.d("phoneBook","verification failed", e)
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            Log.d("phoneBook","code sent: $verificationId")
            storedVerificationId = verificationId
            Log.d("verification",verificationId)
            onCodeSent()
        }
    }

    val options = context.getActivity()?.let {
        PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(it)
            .setCallbacks(callback)
            .build()
    }

    if (options != null) {
        Log.d("phoneBook", options.toString())
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}

fun signInWithPhoneAuthCredential(context: Context, credential: PhoneAuthCredential) {
    FirebaseUtils.firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success
                Log.d("phoneBook", "signInWithCredential:success")
                // TODO: Handle successful sign-in (e.g., navigate to home screen)
            } else {
                // Sign in failed
                Log.w("phoneBook", "signInWithCredential:failure", task.exception)
                // TODO: Handle sign-in failure
            }
        }
}