package com.example.proyecto2moviles.Screens.ui.view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto2moviles.R
import com.example.proyecto2moviles.Screens.ui.viewModel.CalcDumbbellScreenViewModel
import com.example.proyecto2moviles.Screens.ui.viewModel.PRScreenViewModel
import com.example.proyecto2moviles.Screens.ui.viewModel.TrainingScreenViewModel

@Composable
fun ScreenPrincipal(activity: Activity) {

    val backgroundPainter: Painter = painterResource(id = R.drawable.gym2)

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Primera fila de botones
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonConImage(activity, R.drawable.training, "Training") {
                    val navigate = Intent(activity, TrainingScreenViewModel::class.java)
                    activity.startActivity(navigate)
                }
                BotonConImage(activity, R.drawable.pr, "Personal Record") {
                    val navigate = Intent(activity, PRScreenViewModel::class.java)
                    activity.startActivity(navigate)
                }
            }

            // Espaciador entre las filas
            Spacer(modifier = Modifier.height(16.dp))

            // Segunda fila de botones
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonConImage(activity, R.drawable.dumbbell, "Dumbbell Calculate") {
                    val navigate = Intent(activity, CalcDumbbellScreenViewModel::class.java)
                    activity.startActivity(navigate)
                }
                BotonConImage(activity, R.drawable.exit, "Exit") {
                    activity.finishAffinity() // Acción para cerrar la aplicación
                }
            }
        }
    }
}

@Composable
fun BotonConImage(activity: Activity, imageRes: Int, text: String, onClick: (() -> Unit)? = null)  {
    Button(
        onClick = {
            onClick?.invoke()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFC5F2D),
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(8.dp)
            .size(120.dp) // Ajusta el tamaño del botón según tus necesidades
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = text,
                modifier = Modifier.size(60.dp) // Ajusta el tamaño de la imagen
            )
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp // Ajusta el tamaño del texto
            )
        }
    }
}
