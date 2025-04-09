package com.example.f1driversapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.f1driversapp.components.EditableAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var escuderia by remember { mutableStateOf(TextFieldValue("")) }
    var rating by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2F1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Avatar component
            EditableAvatar(
                onEditClick = { /* Implementar lógica para cambiar avatar */ },
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Name field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black
                )
            )

            // Escuderia field
            OutlinedTextField(
                value = escuderia,
                onValueChange = { escuderia = it },
                placeholder = { Text("Escudeto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black
                )
            )

            // Rating field
            OutlinedTextField(
                value = rating,
                onValueChange = { rating = it },
                placeholder = { Text("Rating") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black
                )
            )

            // Confirm button
            Button(
                onClick = { /* Implementar lógica para guardar datos */ },
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Confirmar")
            }
        }
    }
}