package com.example.sanbox_test_api.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sanbox_test_api.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    /**
     * Firebase authentication variable
     **/
    val mAuth = FirebaseAuth.getInstance()

    /**
     * On create lifecycle
     * @param savedInstanceState
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBt: Button = findViewById(R.id.logInBtn)
        val eMailTxt: EditText = findViewById(R.id.emailText)
        val passTxt: EditText = findViewById(R.id.passwordText)

        // Login firebase method
        loginBt.setOnClickListener { _ ->
            val email = eMailTxt.text.toString()
            val pass = passTxt.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult>{
                            task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        Toast.makeText(this,
                            "Efetuou o login com sucesso", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,
                            "Ocorreu um erro ao efetuar um login!", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                Toast.makeText(this,
                    "Por favor preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        val regNewUser: TextView = findViewById(R.id.regNewUser)

        regNewUser.setOnClickListener { _ ->
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
