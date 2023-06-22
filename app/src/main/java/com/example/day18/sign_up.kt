package com.example.day18

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import com.example.day18.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class sign_up : AppCompatActivity() {
    lateinit var bind: ActivitySignUpBinding
    lateinit var db: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.checkedTextView.setOnClickListener {
            bind.checkedTextView.isChecked = !bind.checkedTextView.isChecked
        }

        auth = FirebaseAuth.getInstance()
        var inputemail = bind.upemail
        var inputname = bind.username
        var inputpass = bind.password


        bind.signup.setOnClickListener {
            if (bind.checkedTextView.isChecked) {

                var email = inputemail.text.toString()
                var password = inputpass.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    signUpUser(email, password)
                    var bar = make(bind.root, "Please verify your Email", Snackbar.LENGTH_SHORT)
                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                    bar.setAction("OK", View.OnClickListener {
                        bar.dismiss()
                    })
                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                    bar.show()
                } else {
                    var bar = make(bind.root, "Field should not be empty", Snackbar.LENGTH_SHORT)
                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                    bar.setAction("OK", View.OnClickListener {
                        bar.dismiss()
                    })
                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                    bar.show()
                }
            } else {
//                Toast.makeText(this, "please agree", Toast.LENGTH_SHORT).show()
                var bar = make(bind.root, "please agree", Snackbar.LENGTH_SHORT)
                bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                bar.setAction("OK", View.OnClickListener {
                    bar.dismiss()
                })
                bar.setActionTextColor(Color.parseColor("#59C1BD"))
                bar.show()
            }

        }
    }

    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVerification()
                } else {
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            // Invalid email or password format
                            Log.d(TAG, "Invalid email or password format: ${exception.message}")
                            // Example: Display an error message to the user

                            var bar = make(bind.root, "Invalid format", Snackbar.LENGTH_SHORT)
                            bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                            bar.setAction("OK", View.OnClickListener {
                                bar.dismiss()
                            })
                            bar.setActionTextColor(Color.parseColor("#59C1BD"))
                            bar.show()
                        }

                        is FirebaseAuthUserCollisionException -> {
                            // The email address is already in use by another account
                            Log.d(TAG, "Email address already in use: ${exception.message}")
                            // Example: Display an error message to the user
                            var bar = make(bind.root, "Email Already Exist", Snackbar.LENGTH_SHORT)
                            bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                            bar.setAction("OK", View.OnClickListener {
                                bar.dismiss()
                            })
                            bar.setActionTextColor(Color.parseColor("#59C1BD"))
                            bar.show()
                        }

                        is FirebaseAuthInvalidUserException -> {
                            // User is disabled, deleted, or has an invalid credential
                            Log.d(TAG, "Invalid user: ${exception.message}")
                            // Example: Display an error message to the user
                            var bar = make(bind.root, "Invalid credential", Snackbar.LENGTH_SHORT)
                            bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                            bar.setAction("OK", View.OnClickListener {
                                bar.dismiss()
                            })
                            bar.setActionTextColor(Color.parseColor("#59C1BD"))
                            bar.show()
//                            showErrorDialog("Invalid user account.")
                        }

                        is FirebaseNetworkException -> {
                            // Handle network-related issues
                            val errorMessage = exception.message
                            // Display appropriate error message to the user
                            var bar = make(bind.root, "Network issue", Snackbar.LENGTH_SHORT)
                            bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                            bar.setAction("OK", View.OnClickListener {
                                bar.dismiss()
                            })
                            bar.setActionTextColor(Color.parseColor("#59C1BD"))
                            bar.show()
                        }

                        else -> {
                            // Other sign-up errors
                            Log.d(TAG, "Sign-up failed: ${exception?.message}")
                            // Example: Display a generic error message to the user
                            var bar = make(bind.root, "Else ‼️", Snackbar.LENGTH_SHORT)
                            bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                            bar.setAction("OK", View.OnClickListener {
                                bar.dismiss()
                            })
                            bar.setActionTextColor(Color.parseColor("#59C1BD"))
                            bar.show()
//                            showErrorDialog("An error occurred during sign-up. Please try again later.")
                        }
                    }
                }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser

        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    var bar = make(bind.root, "Mail Sent", Snackbar.LENGTH_SHORT)
                    bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                    bar.setAction("OK", View.OnClickListener {
                        bar.dismiss()
                    })
                    bar.setActionTextColor(Color.parseColor("#59C1BD"))
                    bar.show()
                    var mainname = bind.username.text.toString()

                    db = FirebaseDatabase.getInstance().getReference("Users")
                    var trimuid = user.uid.substring(0, 3)
                    var trimname = mainname
                    var username = "fire${trimname}${trimuid}"
                    var entry = Users(bind.upemail.text.toString(), mainname, username)
                    db.child(user.uid).setValue(entry).addOnSuccessListener {

                        intent = Intent(this, sign_in::class.java)
                        intent.putExtra("main", username)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val exception = task.exception
                    // Email verification sending failed
                }
            }
    }

}


