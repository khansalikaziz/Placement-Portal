package com.lpuportal.visionmilecreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        auth = Firebase.auth
        database = Firebase.database.reference

        val user=auth.currentUser
        val uuid= user?.uid

        findViewById<TextView>(R.id.btn).setOnClickListener {
            startActivity(Intent(applicationContext,DashBoard::class.java))
        }

        if (uuid != null) {
            var email=""
            var name=""
            var reg=""
            var course=""

            database.child("user").child(uuid).child("Email").get().addOnSuccessListener {
                email=it.value.toString()
                findViewById<TextView>(R.id.textView6).setText(email)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
            database.child("user").child(uuid).child("Email").get().addOnSuccessListener {
                email=it.value.toString()
                findViewById<TextView>(R.id.useremail).setText(email)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Name").get().addOnSuccessListener {
                name=it.value.toString()
                findViewById<TextView>(R.id.textView4).setText(name)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Reg").get().addOnSuccessListener {
                reg=it.value.toString()
                findViewById<TextView>(R.id.textView5).setText(reg)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Course").get().addOnSuccessListener {
                course=it.value.toString()
                findViewById<TextView>(R.id.textView7).setText(course)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }
    }
}