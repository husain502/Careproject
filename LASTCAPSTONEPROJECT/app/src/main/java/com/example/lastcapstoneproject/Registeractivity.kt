package com.example.lastcapstoneproject

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.signupname.*

class Registeractivity: AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signupname)

        auth = FirebaseAuth.getInstance()
        btnregister.setOnClickListener {
            val email = logemail.text.toString().trim()
            val password = logpassword.text.toString().trim()
            val username = logusername.text.toString().trim()

            if(email.isEmpty()){
                logemail.error = "Email harus terisi"
                logemail.requestFocus()
                return@setOnClickListener
            }
            if (username.isEmpty()){
                logusername.error = "username harus terisi"
                logusername.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                logemail.error = "Email tidak valid"
                logemail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length<6){
                logpassword.error = "password harus lebih dari 6 karakter"
                logpassword.requestFocus()
                return@setOnClickListener
            }

            registerUser(email,password)
        }

    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@Registeractivity, Homeactivity::class.java).also{
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onStart() {
        super.onStart()
    if(auth.currentUser != null){
        Intent(this@Registeractivity, Homeactivity::class.java).also{
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }}

}