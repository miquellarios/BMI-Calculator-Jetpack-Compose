package com.miquel.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BmiScreen()
                }
            }
        }
    }
}

@Composable
fun BmiScreen() {
    // Estat de la pantalla: quan canvia, Compose torna a dibuixar la UI
    var nom by remember { mutableStateOf("") }
    var alcada by remember { mutableFloatStateOf(170f) }   // en cm
    var pes by remember { mutableIntStateOf(70) }           // en kg
    var resultat by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Calculadora d'IMC", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        // Nom de l'usuari
        OutlinedTextField(
            value = nom,
            onValueChange = {
                nom = it
                error = null
            },
            label = { Text("Nom") },
            singleLine = true,
            isError = error != null,
            supportingText = {
                val missatge = error
                if (missatge != null) Text(missatge)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Alçada amb un Slider
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Alçada: ${alcada.roundToInt()} cm", fontSize = 18.sp)
                Slider(
                    value = alcada,
                    onValueChange = { alcada = it },
                    valueRange = 120f..220f
                )
            }
        }

        // Pes amb botons de - i +
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pes: $pes kg", fontSize = 18.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(onClick = { if (pes > 30) pes-- }) { Text("-", fontSize = 20.sp) }
                    FilledTonalButton(onClick = { if (pes < 250) pes++ }) { Text("+", fontSize = 20.sp) }
                }
            }
        }

        // Botó per calcular, amb validació del nom
        Button(
            onClick = {
                if (nom.isBlank()) {
                    error = "Introdueix el teu nom"
                    resultat = null
                } else {
                    val imc = calcularImc(pes, alcada)
                    resultat = "${nom.trim()}, el teu IMC és %.1f\n${categoria(imc)}".format(imc)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Calcular IMC", fontSize = 18.sp)
        }

        // El resultat només es mostra quan s'ha calculat
        val text = resultat
        if (text != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
        }
    }
}

// IMC = pes (kg) / alçada (m)^2
fun calcularImc(pes: Int, alcadaCm: Float): Float {
    val alcadaM = alcadaCm / 100f
    return pes / (alcadaM * alcadaM)
}

fun categoria(imc: Float): String = when {
    imc < 18.5f -> "Pes insuficient"
    imc < 25f -> "Pes normal"
    imc < 30f -> "Sobrepès"
    else -> "Obesitat"
}
