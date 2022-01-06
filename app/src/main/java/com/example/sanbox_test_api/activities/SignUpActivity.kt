package com.example.sanbox_test_api.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sanbox_test_api.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    /**
     * Firebase authentication variable
     **/
    val mAuth = FirebaseAuth.getInstance()

    /**
     * Firebase reference
     **/
    lateinit var mDatabase: DatabaseReference

    /**
     * On create lifecycle
     * @param savedInstanceState
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mDatabase = FirebaseDatabase.getInstance().getReference("users")

        val regNewUser: Button = findViewById(R.id.signUpBtn)
        val regFirstName: EditText = findViewById(R.id.regFirstName)
        val regLastName: EditText = findViewById(R.id.regLastName)
        val regEmail: EditText = findViewById(R.id.regEmail)
        val regPassword: EditText = findViewById(R.id.regPassword)
        val regCheckPassword: EditText = findViewById(R.id.regCheckPassword)

        // Method to handle register form validations
        regNewUser.setOnClickListener {
            val email = regEmail.text.toString()
            val firstName = regFirstName.text.toString()
            val lastName = regLastName.text.toString()
            val password = regPassword.text.toString()
            val checkPassword = regCheckPassword.text.toString()

            if (email.isNotEmpty() && firstName.isNotEmpty()
                && lastName.isNotEmpty() && password.isNotEmpty() && checkPassword.isNotEmpty()) {
                if (password.length > 5) {
                    if (validateEmailFormat(email)) {
                        if (password == checkPassword) {
                            register(email, password, firstName, lastName)

                        } else {
                            Toast.makeText(this,
                                "Deve repetir a password!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this,
                            "Tem de indicar um email válido", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "A Password deve ter pelo menos seis caracteres",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this,
                    "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    /**
     * Firebase method to register the user
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     **/
    private fun register(email: String, password: String, firstName: String, lastName: String) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    mDatabase.child(uid).child("firstName").setValue(firstName)
                    mDatabase.child(uid).child("lastName").setValue(lastName)
                    Toast.makeText(this,
                        "Registo efetuado com sucesso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this,
                        "Ocorreu um erro no registo!", Toast.LENGTH_SHORT).show()
                }
            })
    }

    /**
     * Email validation
     * @param email
     **/
    private fun validateEmailFormat(email: String): Boolean {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        }
        return false
    }
}
