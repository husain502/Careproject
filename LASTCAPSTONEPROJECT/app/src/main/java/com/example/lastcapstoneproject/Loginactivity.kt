package com.example.lastcapstoneproject

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*

class Loginactivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()

        btnlogin.setOnClickListener {
            val email = loginemail.text.toString().trim()
            val password = loginpassword.text.toString().trim()

            if(email.isEmpty()){
                loginemail.error = "Email harus terisi"
                loginemail.requestFocus()
                return@setOnClickListener
            }


            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                loginemail.error = "Email tidak valid"
                loginemail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length<6){
                loginpassword.error = "password harus lebih dari 6 karakter"
                loginpassword.requestFocus()
                return@setOnClickListener
            }

            loginUser(email,password)


            buttonregister.setOnClickListener{
                Intent(this@Loginactivity, Registeractivity::class.java).also{
                    startActivity(it)
                }

            }
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Intent(this@Loginactivity, Homeactivity::class.java).also{
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
    if (auth.currentUser !=null){
        Intent(this@Loginactivity, Homeactivity::class.java).also{
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }
    }

}