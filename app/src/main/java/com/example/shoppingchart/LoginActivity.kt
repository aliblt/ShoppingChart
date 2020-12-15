package com.example.shoppingchart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        bRegister.setOnClickListener {
            auth.createUserWithEmailAndPassword(etUName.text.toString(), etPass.text.toString())
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Registration is successful", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun login(view: View)
    {
        auth.signInWithEmailAndPassword(etUName.text.toString(), etPass.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "Login is successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else{
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

    }
}