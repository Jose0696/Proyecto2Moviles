package com.example.proyecto2moviles.Screens.ui.view

import android.app.Activity
import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyecto2moviles.R
import java.util.Calendar

@Composable
fun TrainingScreen(activity: Activity) {
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                Text(text = "Selected Date: $selectedDate")
                Spacer(modifier = Modifier.height(16.dp))
                ExerciseList(selectedDate)
            }
        }
    }
}

@Composable
fun ExerciseList(date: String) {
    val exercises = listOf(
        Exercise("Push Ups", "Do 3 sets of 15 reps", "Chest", "Standard"),
        Exercise("Squats", "Do 3 sets of 20 reps", "Legs", "Bodyweight"),
        Exercise("Plank", "Hold for 1 minute", "Core", "Standard"),
        Exercise("Bicep Curls", "Do 3 sets of 12 reps", "Arms", "Dumbbells"),
        Exercise("Lunges", "Do 3 sets of 15 reps", "Legs", "Bodyweight"),
        Exercise("Deadlifts", "Do 3 sets of 8 reps", "Legs", "Barbell"),
        Exercise("Pull Ups", "Do 3 sets of 10 reps", "Back", "Bodyweight"),
        Exercise("Dips", "Do 3 sets of 12 reps", "Triceps", "Parallel Bars"),
        Exercise("Shoulder Press", "Do 3 sets of 12 reps", "Shoulders", "Dumbbells"),
        Exercise("Leg Press", "Do 3 sets of 15 reps", "Legs", "Machine"),
        Exercise("Chest Flys", "Do 3 sets of 12 reps", "Chest", "Cable Machine"),
        Exercise("Hammer Curls", "Do 3 sets of 10 reps", "Arms", "Dumbbells"),
        Exercise("Leg Extensions", "Do 3 sets of 15 reps", "Legs", "Machine"),
        Exercise("Seated Row", "Do 3 sets of 12 reps", "Back", "Cable Machine"),
        Exercise("Calf Raises", "Do 3 sets of 20 reps", "Legs", "Bodyweight"),
        Exercise("Crunches", "Do 3 sets of 20 reps", "Core", "Bodyweight"),
        Exercise("Russian Twists", "Do 3 sets of 15 reps", "Core", "Medicine Ball"),
        Exercise("Cable Triceps Pushdown", "Do 3 sets of 12 reps", "Triceps", "Cable Machine"),
        Exercise("Leg Curl", "Do 3 sets of 15 reps", "Legs", "Machine"),
        Exercise("Reverse Flys", "Do 3 sets of 12 reps", "Back", "Dumbbells")
    )

    LazyColumn {
        item {
            Text(text = "Exercises for $date:")
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(exercises.size) { index ->
            ExerciseItem(exercises[index])
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exercise.name, style = MaterialTheme.typography.headlineLarge)
            Text(text = "Description: ${exercise.description}")
            Text(text = "Muscle Group: ${exercise.muscleGroup}")
            Text(text = "Variant: ${exercise.variant}")
        }
    }
}

data class Exercise(
    val name: String,
    val description: String,
    val muscleGroup: String,
    val variant: String
)
