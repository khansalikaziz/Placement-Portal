package com.lpuportal.visionmilecreation

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


class PdfActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var webView: WebView;
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

         auth = Firebase.auth
         database = Firebase.database.reference
        val user=auth.currentUser
        val uuid= user?.uid

        if (uuid != null) {
            database.child("user").child(uuid).child("resume").get().addOnSuccessListener {
                val url=it.value.toString()
                openWebPage(url)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        findViewById<Button>(R.id.button14).setOnClickListener {
            if (uuid != null) {
                database.child("user").child(uuid).child("resume").get().addOnSuccessListener {
                    val url=it.value.toString()
                    openWebPage(url)
                    Log.i("firebase", "Got value ${it.value}")
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }

    }
    fun openWebPage(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val newIntent = Intent.createChooser(intent, "Open File")
        try {
            startActivity(newIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext,"You don't have proper application",Toast.LENGTH_LONG).show()
        }
    }

}