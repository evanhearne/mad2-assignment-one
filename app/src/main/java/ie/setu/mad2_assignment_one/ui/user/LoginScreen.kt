package ie.setu.mad2_assignment_one.ui.user

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import ie.setu.mad2_assignment_one.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

// Registration Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, context: Context, onNavigateToHome: () -> Unit) {
    val credentialManager = remember { CredentialManager.create(context) }
    val auth = remember { Firebase.auth }
    val coroutineScope = rememberCoroutineScope()

    var showRegisterForm by remember { mutableStateOf(false) }
    if (showRegisterForm) {
        RegisterScreen(
            context = context,
            onNavigateToLoginScreen = { showRegisterForm = false }
        )
    } else {
        // Email string
        var email by remember { mutableStateOf("") }
        // Password string
        var password by remember { mutableStateOf("") }
        Column {
            // Besco Icon
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 80.dp)
            ) {
                Image(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = stringResource(R.string.besco_logo_content_description),
                    modifier = modifier.size(150.dp)
                )
            }
            // Content
            Row(
                modifier = modifier
                    .padding(top = 25.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.main_screen_welcome_text),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.login_screen_paragraph_1),
                    textAlign = TextAlign.Center
                )
            }
            // Email
            Row(
                modifier = modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                TextField(
                    value = email,
                    label = { Text(stringResource(R.string.email_text)) },
                    onValueChange = { email = it })
            }
            // Password
            Row(
                modifier = modifier
                    .padding(top = 25.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                TextField(
                    value = password,
                    label = { Text(stringResource(R.string.password_text)) },
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Row(
                modifier = modifier
                    .padding(top = 25.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                // Login Button
                Column(
                    modifier = modifier
                        .padding(end = 10.dp)
                ) {
                    Button(
                        onClick = {
                            if (email == "" || password == "") {
                                Toast.makeText(
                                    context,
                                    "Cannot log in with empty email or password.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                val auth: FirebaseAuth = Firebase.auth
                                // Send Request to Firebase
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // Toast user their sign in is success
                                            Toast.makeText(
                                                context,
                                                "Sign in successful! Bringing you to the home screen...",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            // Bring user to home screen
                                            onNavigateToHome()
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(
                                                context,
                                                "Unsuccessful User sign in",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Toast.makeText(
                                                context,
                                                task.exception?.message
                                                    ?: "Failure unknown - contact the app developer!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                            }
                        },
                    ) {
                        Text(stringResource(R.string.login_text))
                    }
                }
                // Register button
                Column {
                    Button(
                        onClick = { showRegisterForm = true }
                    ) {
                        Text("I don't have an account.")
                    }
                }
            }
            // https://firebase.google.com/docs/auth/android/google-signin
            Row (
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        // Google Sign In flow
                        val googleIdOption = GetGoogleIdOption.Builder()
                            .setServerClientId(context.getString(R.string.default_web_client_id))
                            .setFilterByAuthorizedAccounts(false)
                            .build()

                        val request = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        // Launch Credential Manager
                        coroutineScope.launch {
                            try {
                                val result = credentialManager.getCredential(
                                    context = context,
                                    request = request
                                )
                                val credential = result.credential
                                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                                    val idToken = googleIdTokenCredential.idToken
                                        val firebaseCredential = getCredential(idToken, null)
                                        auth.signInWithCredential(firebaseCredential)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    // Success
                                                    onNavigateToHome()
                                                } else {
                                                    // Error
                                                    Log.w("LoginScreen", "Firebase auth with Google failed", task.exception)
                                                }
                                            }
                                } else {
                                    Log.w("LoginScreen", "Credential is not of Google ID token type")
                                }
                            } catch (e: GetCredentialException) {
                                Toast.makeText(
                                    context,
                                    "Google sign-in failed: ${e.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between logo and text
                    Text("Sign in with Google")
                }
            }
        }
    }
}

// Registration Screen Preview
@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(context = LocalContext.current, onNavigateToHome = {})
}