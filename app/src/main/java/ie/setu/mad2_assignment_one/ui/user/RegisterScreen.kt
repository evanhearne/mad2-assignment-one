package ie.setu.mad2_assignment_one.ui.user

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.mad2_assignment_one.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// Registration Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, context: Context, onNavigateToLoginScreen: () -> Unit) {
    // Email string
    var email by remember { mutableStateOf("") }
    // Password string
    var password by remember { mutableStateOf("") }
    Column {
        Row {
            // TopAppBar
            TopAppBar(
                title = { Text(stringResource(R.string.register_screen_top_app_bar_title)) },
            )
        }
        // Besco Icon
        Row (modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 80.dp)) {
            Image(imageVector = Icons.Outlined.ShoppingCart, contentDescription = stringResource(R.string.besco_logo_content_description), modifier = modifier.size(150.dp))
        }
        // Content
        Row(
            modifier = modifier
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally)) {
            Text(
                text = stringResource(R.string.main_screen_welcome_text),
                textAlign = TextAlign.Center)
        }
        Row(
            modifier = modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.register_screen_paragraph_1),
                textAlign = TextAlign.Center)
        }
        Row(
            modifier = modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.register_screen_paragraph_2),
                textAlign = TextAlign.Center)
        }
        // Email
        Row(
            modifier = modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            TextField(value = email, label = { Text(stringResource(R.string.email_text)) }, onValueChange = { email = it })
        }
        // Password
        Row(
            modifier = modifier
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            TextField(value = password, label = { Text(stringResource(R.string.password_text)) }, onValueChange = { password = it }, visualTransformation = PasswordVisualTransformation())
        }
        // Register Button
        Row (
            modifier = modifier
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    val auth: FirebaseAuth = Firebase.auth
                    // Send Request to Firebase
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Toast user their registration is success
                                Toast.makeText(context, "Registration successful! Bringing you to login screen...", Toast.LENGTH_LONG).show()
                                // Bring user to login screen
                                onNavigateToLoginScreen()
                            } else {
                                // If sign up fails, display a message to the user.
                                Toast.makeText(context, "Unsuccessful User registration", Toast.LENGTH_LONG).show()
                                Toast.makeText(context, task.exception?.message ?: "Failure unknown - contact the app developer!", Toast.LENGTH_LONG).show()
                            }
                        }
                },
            ) {
                Text(stringResource(R.string.register_button_text))
            }
        }
    }
}

// Registration Screen Preview
@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(context = LocalContext.current, onNavigateToLoginScreen = {})
}