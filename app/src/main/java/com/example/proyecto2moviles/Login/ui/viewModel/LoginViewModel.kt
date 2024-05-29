package com.example.proyecto2moviles.Login.ui.viewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyecto2moviles.Data.model.User
import com.example.proyecto2moviles.Login.ui.view.LoginScreen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginViewModel : ComponentActivity() {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(context = this, databaseReference = databaseReference)
        }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?, User?) -> Unit) {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null && user.email == email && user.password == password) {
                        callback(true, null, user)
                        return
                    }
                }
                callback(false, "Invalid email or password", null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(false, databaseError.message, null)
            }
        })
    }
}

