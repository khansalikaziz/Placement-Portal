package com.lpuportal.visionmilecreation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class PlacementActivity : AppCompatActivity() {
    lateinit var airbus:Button;//id=1
    lateinit var nyala:Button;//id=2
    lateinit var altair:Button;//id=3
    lateinit var opentext:Button;//id=4
    lateinit var bajaj:Button;//id=5

    var ab="";
    var ny="";
    var al="";
    var op="";
    var bj="";

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement)

        auth = Firebase.auth
        database = Firebase.database.reference

        airbus=findViewById(R.id.button2)
        nyala=findViewById(R.id.button4)
        altair=findViewById(R.id.button6)
        opentext=findViewById(R.id.button8)
        bajaj=findViewById(R.id.button10)

        val user = auth.currentUser
        var uuid= user?.uid


        if(uuid!=null){
            database.child("user").child(uuid).child("Drive").child("airbus").get().addOnSuccessListener {
                ab=it.value.toString()
                if(ab.equals("0")){
                    airbus.setText("REGISTER")
                }else{
                    airbus.setText("DEREGISTER")
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("nyala").get().addOnSuccessListener {
                ny=it.value.toString()
                if(ny.equals("0")){
                    nyala.setText("REGISTER")
                }
                else{
                    nyala.setText("DEREGISTER")
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("altair").get().addOnSuccessListener {
                al=it.value.toString()
                if(al.equals("0")){
                    altair.setText("REGISTER")
                }else{
                    altair.setText("DEREGISTER")
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("opentext").get().addOnSuccessListener {
                op=it.value.toString()
                if(op.equals("0")){
                    opentext.setText("REGISTER")
                }
                else{
                    opentext.setText("DEREGISTER")
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("bajaj").get().addOnSuccessListener {
                bj=it.value.toString()
                if(bj.equals("0")){
                    bajaj.setText("REGISTER")
                }else{
                    bajaj.setText("DEREGISTER")
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

        }



        //onclicklisteners
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are You Sure");
        builder.setMessage("This may lead to your debarment from placement services")
        builder.setIcon(R.drawable.announceimg)
        airbus.setOnClickListener {
            if (uuid != null) {
                database.child("user").child(uuid).child("Drive").child("airbus").get().addOnSuccessListener {
                    ab=it.value.toString()
                    if(ab.equals("0")){
                        if (uuid != null) {
                            database.child("user").child(uuid).child("Drive").child("airbus").setValue(1)
                            airbus.setText("DEREGISTER")
                        }
                    }else{
                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                            if (uuid != null) {
                                database.child("user").child(uuid).child("Drive").child("airbus").setValue(0)
                                airbus.setText("REGISTER")
                            }
                        }
                        builder.setNegativeButton("No"){dialogInterface, which ->
                            Toast.makeText(applicationContext,"Deregister Cancelled",Toast.LENGTH_LONG).show()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()

                    }
                    Log.i("firebase", "Got value ${it.value}")
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }
        nyala.setOnClickListener {
            if (uuid != null) {
                database.child("user").child(uuid).child("Drive").child("nyala").get().addOnSuccessListener {
                    ny=it.value.toString()
                    if(ny.equals("0")){
                        if (uuid != null) {
                            database.child("user").child(uuid).child("Drive").child("nyala").setValue(1)
                            nyala.setText("DEREGISTER")
                        }
                    }else{

                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                            if (uuid != null) {
                                database.child("user").child(uuid).child("Drive").child("nyala").setValue(0)
                                nyala.setText("REGISTER")
                            }
                        }
                        builder.setNegativeButton("No"){dialogInterface, which ->
                            Toast.makeText(applicationContext,"Deregister Cancelled",Toast.LENGTH_LONG).show()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                    Log.i("firebase", "Got value ${it.value}")
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }
        altair.setOnClickListener {

            if (uuid != null) {
                database.child("user").child(uuid).child("Drive").child("altair").get().addOnSuccessListener {
                    al=it.value.toString()
                    if(al.equals("0")){
                        if (uuid != null) {
                            database.child("user").child(uuid).child("Drive").child("altair").setValue(1)
                            altair.setText("DEREGISTER")
                        }
                    }else{
                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                            if (uuid != null) {
                                database.child("user").child(uuid).child("Drive").child("altair").setValue(0)
                                altair.setText("REGISTER")
                            }
                        }
                        builder.setNegativeButton("No"){dialogInterface, which ->
                            Toast.makeText(applicationContext,"Deregister Cancelled",Toast.LENGTH_LONG).show()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                    Log.i("firebase", "Got value ${it.value}")
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }
        opentext.setOnClickListener {
            if (uuid != null) {
                database.child("user").child(uuid).child("Drive").child("opentext").get().addOnSuccessListener {
                    op=it.value.toString()
                    if(op.equals("0")){
                        if (uuid != null) {
                            database.child("user").child(uuid).child("Drive").child("opentext").setValue(1)
                            opentext.setText("DEREGISTER")
                        }
                    }else{
                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                            if (uuid != null) {
                                database.child("user").child(uuid).child("Drive").child("opentext").setValue(0)
                                opentext.setText("REGISTER")
                            }
                        }
                        builder.setNegativeButton("No"){dialogInterface, which ->
                            Toast.makeText(applicationContext,"Deregister Cancelled",Toast.LENGTH_LONG).show()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                    Log.i("firebase", "Got value ${it.value}")
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }
        bajaj.setOnClickListener {
            if (uuid != null) {
                database.child("user").child(uuid).child("Drive").child("bajaj").get().addOnSuccessListener {
                    bj=it.value.toString()
                    if(bj.equals("0")){
                        if (uuid != null) {
                            database.child("user").child(uuid).child("Drive").child("bajaj").setValue(1)
                            bajaj.setText("DEREGISTER")
                        }
                    }else{
                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                            if (uuid != null) {
                                database.child("user").child(uuid).child("Drive").child("bajaj").setValue(0)
                                bajaj.setText("REGISTER")
                            }
                        }
                        builder.setNegativeButton("No"){dialogInterface, which ->
                            Toast.makeText(applicationContext,"Deregister Cancelled",Toast.LENGTH_LONG).show()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                    Log.i("firebase", "Got value ${it.value}")
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(applicationContext,JobDescription::class.java))
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(applicationContext,JobDescription::class.java))
        }

        findViewById<Button>(R.id.button5).setOnClickListener {
            startActivity(Intent(applicationContext,JobDescription::class.java))
        }

        findViewById<Button>(R.id.button7).setOnClickListener {
            startActivity(Intent(applicationContext,JobDescription::class.java))
        }

        findViewById<Button>(R.id.button9).setOnClickListener {
            startActivity(Intent(applicationContext,JobDescription::class.java))
        }
    }
}


