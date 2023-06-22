package com.example.day18

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.day18.databinding.ActivityHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.text.FieldPosition
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class home : AppCompatActivity() {

    lateinit var bind: ActivityHomeBinding
    lateinit var db: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var preferences: SharedPreferences
    var fs = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var userlist: ArrayList<link>
    lateinit var editor: SharedPreferences.Editor


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        auth = Firebase.auth
        recyclerView = findViewById(R.id.main)
        recyclerView.layoutManager = LinearLayoutManager(this)





        userlist = arrayListOf()
        userlist.sortByDescending {
            it.time
        }
        val user = auth.currentUser!!

        fs = FirebaseFirestore.getInstance()

        val noteadapter = Noteadapt(userlist)

        recyclerView.adapter = noteadapter





        fs.collection("notes").document(user.uid).collection("mynotes").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val user = data.toObject(link::class.java)
                    if (user != null) {
                        userlist.sortByDescending {
                            it.time
                        }
                        userlist.add(user)
                    }
                }
                recyclerView.adapter = Noteadapt(userlist)
            }
        }




        bind.floatingActionButton.setOnClickListener {

            preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            editor = preferences.edit()
            editor.clear()
            editor.commit()
            intent = Intent(this, welcome::class.java)
            startActivity(intent)
        }

        bind.add.setOnClickListener {
            var inputlink = bind.editText.text
            var sinputlink = bind.editText.text.toString()

            var url = bind.editText2.text
            var surl = bind.editText2.text.toString()

            if (surl.isNotEmpty() && sinputlink.isNotEmpty()) {
                val view = layoutInflater.inflate(R.layout.addview, null)
                val linkname = view.findViewById<TextView>(R.id.Linkname)
                val link = view.findViewById<TextView>(R.id.Url)

                linkname.text = sinputlink
                link.text = surl


                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val formatted = current.format(formatter)

//            val entry = input(sinputlink, surl, formatted)

                val entry = hashMapOf(
                    "name" to sinputlink,
                    "url" to surl,
                    "time" to formatted
                )

                fs.collection("notes").document(user.uid).collection("mynotes").document()
                    .set(entry)
                    .addOnSuccessListener {
//                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                        fs.collection("notes").document(user.uid).collection("mynotes")
                            .orderBy(
                                "time",
                                com.google.firebase.firestore.Query.Direction.DESCENDING
                            )
                        val user = link(sinputlink, surl, formatted)
                        userlist.add(user)
                        userlist.sortByDescending {
                            it.time
                        }
                        recyclerView.adapter = Noteadapt(userlist)
                    }.addOnFailureListener {
                        Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                    }


            } else {
                var bar = make(bind.root, "Field should not be empty", Snackbar.LENGTH_SHORT)
                bar.setBackgroundTint(Color.parseColor("#0D4C92"))
                bar.setAction("OK", View.OnClickListener {
                    bar.dismiss()
                })
                bar.setActionTextColor(Color.parseColor("#59C1BD"))
                bar.show()
            }
        }
    }

    fun onItem(position: Int) {
        userlist.removeAt(position)
        recyclerView.adapter?.notifyItemRemoved(position)
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
    }

}


