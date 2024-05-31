package com.example.proyecto2moviles.Screens.ui.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto2moviles.Data.model.Training
import com.example.proyecto2moviles.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

@SuppressLint("UnrememberedMutableState")
@Composable
fun TrainingScreen(databaseReference: DatabaseReference) {
    var trainingList by mutableStateOf<List<Training>>(emptyList())

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Training")

    database.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val list = mutableListOf<Training>()
            for (data in snapshot.children) {
                val training = data.getValue(Training::class.java)
                training?.let {
                    list.add(it)
                }
            }
            trainingList = list
        }

        override fun onCancelled(error: DatabaseError) {

        }
    })

    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
        }, year, month, day
    )


    val backgroundPainter: Painter = painterResource(id = R.drawable.gym1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 1.0f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 1.0f)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Training", color = Color.White, fontSize = 28.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                datePickerDialog.show()
            },
                modifier = Modifier
                    .height(50.dp)
                    .width(140.dp),
                shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
                enabled = true,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 30.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFC5F2D),
                    contentColor = Color.Green,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.White
                )
            ) {
                Text(text = "Select Date", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedDate.isNotEmpty()) {
                Text(text = "Selected Date: $selectedDate", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(trainingList) { training ->
                        TrainingItem(training)
                    }
                }
            }
        }
    }
}

@Composable
fun TrainingItem(training: Training) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = training.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description: ${training.description}")
            Text(text = "Muscle Group: ${training.muscleGroup}")
            Text(text = "Variant: ${training.variant}")
        }
    }
}
