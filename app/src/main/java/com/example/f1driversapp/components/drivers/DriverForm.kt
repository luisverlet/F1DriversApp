package com.example.f1driversapp.components.drivers

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.f1driversapp.R
import com.example.f1driversapp.components.buttons.ConfirmButton
import com.example.f1driversapp.utils.DriverValidator
import com.example.f1driversapp.utils.DriverCreator
import kotlinx.coroutines.CoroutineScope

@Composable
fun DriverForm(
    onDriverCreated: () -> Unit,
    context: Context,
    coroutineScope: CoroutineScope
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var escuderia by remember { mutableStateOf(TextFieldValue("")) }
    var rating by remember { mutableStateOf(TextFieldValue("")) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var nameError by remember { mutableStateOf("") }
    var escuderiaError by remember { mutableStateOf("") }
    var ratingError by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))


        EditableAvatar(
            selectedImageUri = selectedImageUri,
            onImageSelected = { uri -> selectedImageUri = uri },
            modifier = Modifier.padding(bottom = 24.dp)
        )


        DriverInputField(
            value = name,
            onValueChange = {
                name = it
                nameError = ""
            },
            placeholder = stringResource(R.string.input_name),
            isError = nameError.isNotEmpty(),
            errorMessage = nameError,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        DriverInputField(
            value = description,
            onValueChange = { description = it },
            placeholder = stringResource(R.string.input_description),
            modifier = Modifier.padding(bottom = 16.dp)
        )


        DriverInputField(
            value = escuderia,
            onValueChange = {
                escuderia = it
                escuderiaError = ""
            },
            placeholder = stringResource(R.string.input_squad),
            isError = escuderiaError.isNotEmpty(),
            errorMessage = escuderiaError,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        DriverInputField(
            value = rating,
            onValueChange = {
                if (it.text.isEmpty() || it.text.matches(Regex("^\\d{1,3}$"))) {
                    rating = it
                    ratingError = ""
                }
            },
            placeholder = stringResource(R.string.input_rating),
            isError = ratingError.isNotEmpty(),
            errorMessage = ratingError,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Confirm button
        ConfirmButton(
            isLoading = isLoading,
            onClick = {
                if (DriverValidator.validateFields(
                        context,
                        name.text,
                        escuderia.text,
                        rating.text,
                        onNameError = { nameError = it },
                        onEscuderiaError = { escuderiaError = it },
                        onRatingError = { ratingError = it }
                    )
                ) {
                    DriverCreator.createDriver(
                        name = name.text,
                        description = description.text,
                        escuderia = escuderia.text,
                        rating = rating.text.toInt(),
                        imageUri = selectedImageUri,
                        context = context,
                        coroutineScope = coroutineScope,
                        onStartLoading = { isLoading = true },
                        onFinishLoading = { isLoading = false },
                        onSuccess = {
                            Toast.makeText(context, context.getString(R.string.driver_added_success), Toast.LENGTH_SHORT).show()
                            onDriverCreated()
                        },
                        onError = { error ->
                            Toast.makeText(
                                context,
                                context.getString(R.string.error_prefix, error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            }
        )
    }
}