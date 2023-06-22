package com.example.day18

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.day18.databinding.ActivitySignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class sign_in : AppCompatActivity() {
    lateinit var bind: ActivitySignInBinding
    lateinit var auth: FirebaseAuth
    lateinit var db: DatabaseReference
    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(bind.root)

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        editor = preferences.edit()

        bind.reset.setOnClickListener {
            if (bind.email.text.toString().isNotEmpty()) {

                Firebase.auth.sendPasswordResetEmail(bind.email.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                            var bar =
                                Snackbar.make(bind.root, "Reset mail sent", Snackbar.LENGTH_SHORT)
                            bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                            bar.setAction("OK", View.OnClickListener {
                                bar.dismiss()
                            })
                            bar.setActionTextColor(Color.parseColor("#59C1BD"))
                            bar.show()
                        } else {
                            val e = task.exception

                            when (e) {
                                is FirebaseAuthInvalidUserException -> {
                                    // Handle the case when the user's email is not registered
                                    val errorMessage = e.message
                                    var bar =
                                        Snackbar.make(
                                            bind.root,
                                            "Email not registered",
                                            Snackbar.LENGTH_SHORT
                                        )
                                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                    bar.setAction("OK", View.OnClickListener {
                                        bar.dismiss()
                                    })
                                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                                    bar.show()
                                    // Display appropriate error message to the user
                                }

                                is FirebaseAuthRecentLoginRequiredException -> {
                                    // Handle the case when recent login is required
                                    val errorMessage = e.message
                                    // Display appropriate error message to the user
                                    Toast.makeText(this, "Oops!", Toast.LENGTH_SHORT).show()
                                }

                                is FirebaseNetworkException -> {
                                    // Handle network-related issues
                                    val errorMessage = e.message
                                    var bar =
                                        Snackbar.make(
                                            bind.root,
                                            "Network Issue",
                                            Snackbar.LENGTH_SHORT
                                        )
                                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                    bar.setAction("OK", View.OnClickListener {
                                        bar.dismiss()
                                    })
                                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                                    bar.show()
                                    // Display appropriate error message to the user
                                }

                                is FirebaseException -> {
                                    // Handle general Firebase errors
                                    val errorMessage = e.message
                                    var bar =
                                        Snackbar.make(
                                            bind.root,
                                            "Invalid Format",
                                            Snackbar.LENGTH_SHORT
                                        )
                                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                    bar.setAction("OK", View.OnClickListener {
                                        bar.dismiss()
                                    })
                                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                                    bar.show()
                                    // Display appropriate error message to the user
                                }

                                else -> {
                                    var bar =
                                        Snackbar.make(
                                            bind.root,
                                            "Try again later ",
                                            Snackbar.LENGTH_SHORT
                                        )
                                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                    bar.setAction("OK", View.OnClickListener {
                                        bar.dismiss()
                                    })
                                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                                    bar.show()
                                    // Display a generic error message to the user
                                }
                            }
                        }
                    }
            }

        }
        bind.checkedTextView.setOnClickListener {
            bind.checkedTextView.isChecked = !bind.checkedTextView.isChecked

        }
        val email = bind.email
        val password = bind.password
        auth = Firebase.auth
        bind.signin.setOnClickListener {
            if (bind.checkedTextView.isChecked) {
                if (email.text.toString().isNotEmpty() && password.text.toString()
                        .isNotEmpty()
                ) {
                    auth.signInWithEmailAndPassword(
                        email.text.toString(),
                        password.text.toString()
                    )
                        .addOnCompleteListener { task ->
                            val user = auth.currentUser

                            if (task.isSuccessful) {
                                if (user != null && user.isEmailVerified) {

                                    editor.putString("email", email.text.toString())
                                    editor.putString("pass", password.text.toString())
                                    editor.putString("uid", user.uid)
                                    editor.commit()
                                    intent = Intent(this, home::class.java)
                                    startActivity(intent)
                                    finish()

                                } else {

                                    Toast.makeText(this, "verfiy email", Toast.LENGTH_SHORT)
                                        .show()
                                    sendEmailVerification()
                                }
                            } else {
                                val exception = task.exception
                                when (exception) {
                                    is FirebaseAuthInvalidCredentialsException -> {
                                        // Invalid email or password format
                                        Log.d(
                                            ContentValues.TAG,
                                            "Invalid email or password format: ${exception.message}"
                                        )
                                        // Example: Display an error message to the user
                                        var bar = Snackbar.make(
                                            bind.root,
                                            "Invalid format",
                                            Snackbar.LENGTH_SHORT
                                        )
                                        bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                        bar.setAction("OK", View.OnClickListener {
                                            bar.dismiss()
                                        })
                                        bar.setActionTextColor(Color.parseColor("#59C1BD"))
                                        bar.show()
                                    }

                                    is FirebaseNetworkException -> {
                                        // Handle network-related issues
                                        val errorMessage = exception.message
                                        var bar = Snackbar.make(
                                            bind.root,
                                            "Network issue",
                                            Snackbar.LENGTH_SHORT
                                        )
                                        bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                        bar.setAction("OK", View.OnClickListener {
                                            bar.dismiss()
                                        })
                                        bar.setActionTextColor(Color.parseColor("#59C1BD"))
                                        bar.show()
                                        // Display appropriate error message to the user
                                    }

                                    is FirebaseAuthUserCollisionException -> {
                                        // The email address is already in use by another account
                                        Log.d(
                                            ContentValues.TAG,
                                            "Email address already in use: ${exception.message}"
                                        )
                                        // Example: Display an error message to the user
//                            showErrorDialog("The email address is already in use by another account.")
                                        var bar = Snackbar.make(
                                            bind.root,
                                            "Email Already Exist",
                                            Snackbar.LENGTH_SHORT
                                        )
                                        bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                        bar.setAction("OK", View.OnClickListener {
                                            bar.dismiss()
                                        })
                                    }

                                    is FirebaseAuthInvalidUserException -> {
                                        // User is disabled, deleted, or has an invalid credential
                                        Log.d(
                                            ContentValues.TAG,
                                            "Invalid user: ${exception.message}"
                                        )
                                        // Example: Display an error message to the user
                                        var bar = Snackbar.make(
                                            bind.root,
                                            "Invalid credential",
                                            Snackbar.LENGTH_SHORT
                                        )
                                        bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                                        bar.setAction("OK", View.OnClickListener {
                                            bar.dismiss()
                                        })
                                        bar.setActionTextColor(Color.parseColor("#59C1BD"))
                                        bar.show()
//                            showErrorDialog("Invalid user account.")
                                    }

                                    else -> {
                                        // Other sign-up errors
                                        Log.d(
                                            ContentValues.TAG,
                                            "Sign-up failed: ${exception?.message}"
                                        )
                                        // Example: Display a generic error message to the user
                                        Toast.makeText(this, "else", Toast.LENGTH_SHORT).show()
//                            showErrorDialog("An error occurred during sign-up. Please try again later.")
                                    }
                                }
                            }
                        }
                } else {
                    var bar =
                        Snackbar.make(bind.root, "Field should not be empty", Snackbar.LENGTH_SHORT)
                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                    bar.setAction("OK", View.OnClickListener {
                        bar.dismiss()
                    })
                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                    bar.show()
                }
            }
            var bar = Snackbar.make(bind.root, "please agree", Snackbar.LENGTH_SHORT)
            bar.setBackgroundTint(Color.parseColor("#0D4C92"))
            bar.setAction("OK", View.OnClickListener {
                bar.dismiss()
            })
            bar.setActionTextColor(Color.parseColor("#59C1BD"))
            bar.show()
        }
    }


    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
                var bar = Snackbar.make(bind.root, "Mail Sent", Snackbar.LENGTH_SHORT)
                bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                bar.setAction("OK", View.OnClickListener {
                    bar.dismiss()
                })
                bar.setActionTextColor(Color.parseColor("#59C1BD"))
                bar.show()

            }
        // [END send_email_verification]
    }
}
