package com.lpuportal.visionmilecreation

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*


class UserProfile : AppCompatActivity() {
    lateinit var progress:ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    val GALLERY_REQUEST_CODE=4
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
        //Uploading pdf
        findViewById<Button>(R.id.button16).setOnClickListener {

            selectPdfFromGallery()
        }
    }
    private fun selectPdfFromGallery() {

        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Please select..."
            ),
            GALLERY_REQUEST_CODE
        )
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (requestCode == GALLERY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
            && data.data != null
        ) {

            // Get the Uri of data
            val file_uri = data.data
            if (file_uri != null) {
                uploadPdfToFirebase(file_uri)
            }
        }
    }
    private fun uploadPdfToFirebase(fileUri: Uri) {
        if (fileUri != null) {


            auth = Firebase.auth
            database = Firebase.database.reference

            val user=auth.currentUser
            val uuid= user?.uid
            val fileName = uuid.toString() +".pdf"
            val refStorage = FirebaseStorage.getInstance().reference.child("pdfs/$uuid/$fileName")
            progress=findViewById(R.id.progressBar)
            progress.visibility=View.VISIBLE

            refStorage.putFile(fileUri)
                .addOnSuccessListener(

                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            if (uuid != null) {
                                database.child("user").child(uuid).child("resume").setValue(imageUrl)
                            }
                            Toast.makeText(applicationContext,imageUrl, Toast.LENGTH_LONG).show()
                            progress.visibility=View.GONE

                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }
}