package com.example.proyecto2moviles.Screens.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto2moviles.Data.model.User
import com.example.proyecto2moviles.R
import com.google.firebase.database.DatabaseReference

@Composable
fun PRScreen(user: User, databaseReference: DatabaseReference) {
    var benchPress by remember { mutableIntStateOf(user.benchPress) }
    var shoulderPress by remember { mutableIntStateOf(user.shoulderPress) }
    var snatch by remember { mutableIntStateOf(user.snatch) }
    var clean by remember { mutableIntStateOf(user.clean) }
    var deadLift by remember { mutableIntStateOf(user.deadLift) }

    fun updateUserField(field: String, value: Int) {
        databaseReference.child(user.id).child(field).setValue(value)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.gym4),
            contentDescription = "Background Image",
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(145.dp))

            Text(text = "Personal Records", fontSize = 28.sp, color = Color.White)

            RecordField(label = "Bench Press", value = benchPress, max = 1450) { newValue ->
                benchPress = newValue
                updateUserField("benchPress", newValue)
            }

            RecordField(label = "Shoulder Press", value = shoulderPress, max = 650) { newValue ->
                shoulderPress = newValue
                updateUserField("shoulderPress", newValue)
            }

            RecordField(label = "Snatch", value = snatch, max = 550) { newValue ->
                snatch = newValue
                updateUserField("snatch", newValue)
            }

            RecordField(label = "Clean", value = clean, max = 600) { newValue ->
                clean = newValue
                updateUserField("clean", newValue)
            }

            RecordField(label = "Deadlift", value = deadLift, max = 1200) { newValue ->
                deadLift = newValue
                updateUserField("deadLift", newValue)
            }
        }
    }
}

@Composable
fun RecordField(label: String, value: Int, max: Int, onValueChange: (Int) -> Unit) {
    var inputValue by remember { mutableStateOf(value.toString()) }

    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$label: $value lb", color = Color.White)

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = inputValue,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    val intValue = filteredValue.toIntOrNull()
                    if (intValue != null && intValue <= max) {
                        inputValue = filteredValue
                        onValueChange(intValue)
                    } else if (intValue == null && filteredValue.isEmpty()) {
                        inputValue = ""
                        onValueChange(0)
                    }
                },
                modifier = Modifier
                    .width(100.dp)
                    .background(Color.White),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = {
                    Text(text = "$max", color = Color.Gray)
                },
            )
        }
    }
}