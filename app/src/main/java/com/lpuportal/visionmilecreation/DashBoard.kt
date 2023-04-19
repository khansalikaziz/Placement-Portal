package com.lpuportal.visionmilecreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


import androidx.appcompat.app.ActionBarDrawerToggle



import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout


import com.google.android.material.navigation.NavigationView

class DashBoard : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var nameTextView:TextView
    lateinit var rankTextView:TextView
    lateinit var name:Array<String>
    lateinit var progress:ProgressBar;


    lateinit var ll:LinearLayout
    lateinit var ll1:LinearLayout

    lateinit var drawer:DrawerLayout
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        auth = Firebase.auth
        database = Firebase.database.reference
        val user=auth.currentUser
        val uuid= user?.uid

        nameTextView=findViewById(R.id.hel)
        rankTextView=findViewById(R.id.rep)
        progress=findViewById(R.id.pg)
        ll=findViewById(R.id.inc)
        ll1=findViewById(R.id.inc1)

        var placementBtn:View=ll.findViewById(R.id.placement)
        placementBtn.setOnClickListener {
            startActivity(Intent(this,PlacementActivity::class.java))
        }

        var profileBtn:View=ll.findViewById(R.id.profile)
        profileBtn.setOnClickListener {
            startActivity(Intent(this,UserProfile::class.java))
        }

        var noticeBtn:View=ll1.findViewById(R.id.notice)
        noticeBtn.setOnClickListener {
            startActivity(Intent(this,NoticeActivity::class.java))
        }

        var policyBtn:View=ll1.findViewById(R.id.policy)
        policyBtn.setOnClickListener {
            startActivity(Intent(this,PolicyActivity::class.java))
        }
        //Updating Name
        if (uuid != null) {
            database.child("user").child(uuid).child("Name").get().addOnSuccessListener {
                name= it.value.toString().split(" ").toTypedArray()
                nameTextView.setText("Hello "+name[0]+"!")
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        //Getting Avg

        var avg=0.0f
        var ab=0
        var ny=0
        var al=0
        var op=0
        var bj=0
        if(uuid!=null){
            database.child("user").child(uuid).child("Drive").child("airbus").get().addOnSuccessListener {
                ab= Integer.parseInt(it.value.toString())
                if((ab+ny+al+op+bj)>=1){
                    avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("nyala").get().addOnSuccessListener {
                ny=Integer.parseInt(it.value.toString())
                if((ab+ny+al+op+bj)>=1){
                    avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("altair").get().addOnSuccessListener {
                al=Integer.parseInt(it.value.toString())
                if((ab+ny+al+op+bj)>=1){
                    avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("opentext").get().addOnSuccessListener {
                op=Integer.parseInt(it.value.toString())
                if((ab+ny+al+op+bj)>=1){
                    avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Drive").child("bajaj").get().addOnSuccessListener {
                bj=Integer.parseInt(it.value.toString())
                if((ab+ny+al+op+bj)>=1){
                    avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
                }
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            database.child("user").child(uuid).child("Email").get().addOnSuccessListener {
                val email=it.value.toString()
                findViewById<TextView>(R.id.uemail).setText(email)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
            database.child("user").child(uuid).child("Name").get().addOnSuccessListener {
                val name=it.value.toString()
                findViewById<TextView>(R.id.uname).setText(name)
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

        }

        //End Avg

        //Updating Rank
        rankTextView.setOnClickListener {
            progress.progress= avg.toInt()
            if (avg>=80){
                rankTextView.setText("You are in Dean List \uD835\uDE81\uD835\uDE8E\uD835\uDE8F\uD835\uDE9B\uD835\uDE8E\uD835\uDE9C\uD835\uDE91")
            }else if(avg>=40){
                rankTextView.setText("You are in S1 List \uD835\uDE81\uD835\uDE8E\uD835\uDE8F\uD835\uDE9B\uD835\uDE8E\uD835\uDE9C\uD835\uDE91")
            }
            else{
                rankTextView.setText("You are in S2 List \uD835\uDE81\uD835\uDE8E\uD835\uDE8F\uD835\uDE9B\uD835\uDE8E\uD835\uDE9C\uD835\uDE91")
            }

        }


        //Drawer start

        var toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        var actionBarDrawerToggle = ActionBarDrawerToggle(this,drawer,toolbar,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        //Drawer End


//        var t: Toolbar =findViewById(R.id.toolbar);
//        t.setTitle("Placements")
//        setSupportActionBar(t)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth = Firebase.auth
        database = Firebase.database.reference
        val user=auth.currentUser
        val uuid= user?.uid
        val id=item.itemId
        if (id==R.id.vprofile){
            startActivity(Intent(this,UserProfile::class.java))
        }else if(id==R.id.logout){
            Firebase.auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }
        else if(id==R.id.resume){
            if (uuid != null) {
                database.child("user").child(uuid).child("resume").get().addOnSuccessListener {
                    val imgUrl=it.value.toString()
                    if(imgUrl.equals("0")){
                        Toast.makeText(applicationContext,"Upload Your Resume First",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,UserProfile::class.java))
                    }else{
                        startActivity(Intent(this,PdfActivity::class.java))
                    }

                }.addOnFailureListener{
                    Toast.makeText(applicationContext,"Error...Try Again",Toast.LENGTH_LONG).show()
                }
            }

        }
        return true
    }
    override fun onStart() {
        super.onStart()
    auth = Firebase.auth
    database = Firebase.database.reference
    val user=auth.currentUser
    val uuid= user?.uid


    rankTextView=findViewById(R.id.rep)
    progress=findViewById(R.id.pg)
    var avg=0.0f
    var ab=0
    var ny=0
    var al=0
    var op=0
    var bj=0
    if(uuid!=null){
        database.child("user").child(uuid).child("Drive").child("airbus").get().addOnSuccessListener {
            ab= Integer.parseInt(it.value.toString())
            if((ab+ny+al+op+bj)>=1){
                avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
            }
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        database.child("user").child(uuid).child("Drive").child("nyala").get().addOnSuccessListener {
            ny=Integer.parseInt(it.value.toString())
            if((ab+ny+al+op+bj)>=1){
                avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
            }
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        database.child("user").child(uuid).child("Drive").child("altair").get().addOnSuccessListener {
            al=Integer.parseInt(it.value.toString())
            if((ab+ny+al+op+bj)>=1){
                avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
            }
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        database.child("user").child(uuid).child("Drive").child("opentext").get().addOnSuccessListener {
            op=Integer.parseInt(it.value.toString())
            if((ab+ny+al+op+bj)>=1){
                avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
            }
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        database.child("user").child(uuid).child("Drive").child("bajaj").get().addOnSuccessListener {
            bj=Integer.parseInt(it.value.toString())
            if((ab+ny+al+op+bj)>=1){
                avg= ((((ab+ny+al+op+bj).toFloat()*20)/100)*100)
            }

            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

    }

    //End Avg

    //Updating Rank
    rankTextView.setOnClickListener {
        progress.progress= avg.toInt()
        if (avg>=80){
            rankTextView.setText("You are in Dean List (\uD835\uDE81\uD835\uDE8E\uD835\uDE8F\uD835\uDE9B\uD835\uDE8E\uD835\uDE9C\uD835\uDE91)")
        }else if(avg>=40){
            rankTextView.setText("You are in S1 List (\uD835\uDE81\uD835\uDE8E\uD835\uDE8F\uD835\uDE9B\uD835\uDE8E\uD835\uDE9C\uD835\uDE91)")
        }
        else{
            rankTextView.setText("You are in S2 List (\uD835\uDE81\uD835\uDE8E\uD835\uDE8F\uD835\uDE9B\uD835\uDE8E\uD835\uDE9C\uD835\uDE91)")
        }

    }

        //Drawer


    }
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.userprof ->startActivity(Intent(applicationContext,UserProfile::class.java))
            R.id.userRes -> {
                auth = Firebase.auth
                database = Firebase.database.reference
                val user=auth.currentUser
                val uuid= user?.uid
                if (uuid != null) {
                    database.child("user").child(uuid).child("resume").get().addOnSuccessListener {
                        val imgUrl=it.value.toString()
                        if(imgUrl.equals("0")){
                            Toast.makeText(applicationContext,"Upload Your Resume First",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,UserProfile::class.java))
                        }else{
                            startActivity(Intent(this,PdfActivity::class.java))
                        }

                    }.addOnFailureListener{
                        Toast.makeText(applicationContext,"Error...Try Again",Toast.LENGTH_LONG).show()
                    }
                }
            }
            R.id.userann -> startActivity(Intent(applicationContext,NoticeActivity::class.java))
            R.id.userlog -> {
                Firebase.auth.signOut()
                startActivity(Intent(this,MainActivity::class.java))
            }

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}