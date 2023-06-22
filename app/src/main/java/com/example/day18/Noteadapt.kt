package com.example.day18

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.p2p.WifiP2pManager.NetworkInfoListener
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Noteadapt(private val userlist: ArrayList<link>) :


    RecyclerView.Adapter<Noteadapt.myviewholder>() {

    class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Linkname: TextView = itemView.findViewById(R.id.Linkname)
        val url: TextView = itemView.findViewById(R.id.Url)
        val inputtime: TextView = itemView.findViewById(R.id.time)
        var one: String = ""
        var fs = Firebase.firestore
        lateinit var auth: FirebaseAuth




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.addview, parent, false)

        return myviewholder(itemView)

    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        holder.Linkname.text = userlist[position].name.toString()
        holder.url.text = userlist[position].url.toString()
        holder.inputtime.text = userlist[position].time
        holder.auth = FirebaseAuth.getInstance()
        holder.one = userlist[position].url.toString()


    }


    override fun getItemCount(): Int {
        return userlist.size
    }



}


