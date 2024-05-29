package com.example.proyecto2moviles.Screens.ui.viewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import com.example.proyecto2moviles.Data.model.User
import com.example.proyecto2moviles.Screens.ui.view.PRScreen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class PRScreenViewModel : ComponentActivity() {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userJson = intent.getStringExtra("user")
        val user = Gson().fromJson(userJson, User::class.java)

        val userState = mutableStateOf<User?>(null)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val tempUser = userSnapshot.getValue(User::class.java)
                    if (tempUser != null && tempUser.id == user.id) {
                        userState.value = tempUser
                        return
                    }
                }
                userState.value = user
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        setContent {
            userState.value?.let { currentUser ->
                PRScreen(currentUser, databaseReference = databaseReference)
            }
        }
    }
}
