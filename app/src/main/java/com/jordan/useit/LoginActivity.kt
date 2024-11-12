package com.jordan.useit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jordan.useit.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    // Boolean flag to set user as admin
    private val isAdmin = false // Set this to true to create an admin account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            signIn(email, password)
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            createAccount(email, password, isAdmin)
        }

        binding.skipButton.setOnClickListener {
            // Allow the user to proceed without logging in
            signInAnonymously()
        }
    }

    private fun signIn(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun createAccount(email: String, password: String, isAdmin: Boolean) {
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Account creation success, add user to Firestore with role
                    val user = auth.currentUser
                    if (user != null) {
                        addUserToFirestore(user.uid, email, isAdmin)
                    }
                    // Sign in the user
                    signIn(email, password)
                } else {
                    // If sign-up fails, display a message to the user.
                    Toast.makeText(baseContext, "Registration failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToFirestore(userId: String, email: String, isAdmin: Boolean) {
        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
        val userData = mapOf(
            "email" to email,
            "role" to if (isAdmin) "admin" else "user"
        )
        userRef.set(userData)
            .addOnSuccessListener {
                // User added successfully
                Toast.makeText(this, "User added to Firestore", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(this, "Failed to add user to Firestore", Toast.LENGTH_SHORT).show()
            }
    }

    private fun signInAnonymously() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success, navigate to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(baseContext, "Anonymous authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
