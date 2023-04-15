package com.lpuportal.visionmilecreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        emailText=findViewById(R.id.emailBox)
        passwordText=findViewById(R.id.passwordBox)
        var login: Button =findViewById(R.id.regBtn)

        login.setOnClickListener {
            var email=emailText.text.toString()
            var password=passwordText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if(!email.isEmpty() && !password.isEmpty()){
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Authentication Success",
                                Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser
                            var uuid= user?.uid
                            startActivity(Intent(this,DashBoard::class.java))
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }else{
                        Toast.makeText(baseContext, "Fill all inputs",
                            Toast.LENGTH_SHORT).show()
                    }

                }
        }


        findViewById<TextView>(R.id.signupbtn).setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this,DashBoard::class.java))
        }
    }
}