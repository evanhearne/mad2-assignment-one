package ie.setu.mad2_assignment_one.ui.user

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import android.net.Uri
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import coil3.compose.rememberAsyncImagePainter
import ie.setu.mad2_assignment_one.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch

// Registration Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, context: Context, onNavigateToLoginScreen: () -> Unit) {
    // Display name string
    var displayNameString by remember { mutableStateOf("") }
    // Email string
    var email by remember { mutableStateOf("") }
    // Password string
    var password by remember { mutableStateOf("") }
    // URI String
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    // Image URI Launcher
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }
    // Percentage Progress for image upload
    var uploadProgress by remember { mutableIntStateOf(0) }
    // co routine scope for bringing user back on successful image upload
    var scope = rememberCoroutineScope()
    Column {
        // Display profile picture before upload
        if (imageUri?.path?.isEmpty() == false) {
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "My Image",
                    modifier = modifier.size(150.dp)
                )
            }
        } else {
            // Besco Icon
            Row (modifier = modifier
                .align(Alignment.CenterHorizontally)) {
                Image(imageVector = Icons.Outlined.ShoppingCart, contentDescription = stringResource(R.string.besco_logo_content_description), modifier = modifier.size(150.dp))
            }
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
        // Upload button
        Row(modifier = modifier
            .padding(top = 25.dp)
            .align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                launcher.launch("image/*")
            }
            ) {
                Text("Upload Profile Picture")
            }
        }
        // Display Name
        Row(
            modifier = modifier
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            TextField(value = displayNameString, label = { Text(stringResource(R.string.display_name)) }, onValueChange = { displayNameString = it })
        }
        // Email
        Row(
            modifier = modifier
                .padding(top = 25.dp)
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
                    if (!email.isEmpty() && !password.isEmpty()) {
                    val auth: FirebaseAuth = Firebase.auth
                    // Send Request to Firebase
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Toast user their registration is success
                                Toast.makeText(context, "Registration successful! Bringing you to login screen...", Toast.LENGTH_LONG).show()
                                // Firebase storage path for images
                                var storagePath =
                                    Firebase.storage.reference.child("/profile_pictures/$email")
                                // Upload file
                                val uploadTask = storagePath.putFile(imageUri!!)
                                // If upload fail...
                                uploadTask.addOnFailureListener {
                                    // Add toast to alert the user
                                    Toast.makeText(
                                        context,
                                        "Unable to upload profile image - please check the file and try again.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    uploadProgress = -1
                                }.addOnSuccessListener { // if upload success...
                                    // Also add toast to alert the user
                                    Toast.makeText(
                                        context,
                                        "Profile image uploaded successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    uploadProgress = -1
                                }
                                // Modify user profile with photoUrl and displayName
                                if (auth.currentUser != null && !displayNameString.isEmpty()) {
                                    uploadTask.addOnSuccessListener {
                                        storagePath.downloadUrl.addOnSuccessListener { uri ->
                                            val user = auth.currentUser
                                            val profileUpdate = userProfileChangeRequest {
                                                displayName = displayNameString
                                                photoUri = uri
                                            }
                                            user?.updateProfile(profileUpdate)
                                                ?.addOnSuccessListener {
                                                    Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
                                                    scope.launch {
                                                        onNavigateToLoginScreen()
                                                    }
                                                }
                                                ?.addOnFailureListener {
                                                    Toast.makeText(context, "Failed to update profile.", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    }.addOnFailureListener {
                                        Toast.makeText(context, "Failed to upload image.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                // Bring user to login screen
                                onNavigateToLoginScreen()
                            } else {
                                // If sign up fails, display a message to the user.
                                Toast.makeText(context, "Unsuccessful User registration", Toast.LENGTH_LONG).show()
                                Toast.makeText(context, task.exception?.message ?: "Failure unknown - contact the app developer!", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "User email and password fields empty - please fill before trying again.", Toast.LENGTH_SHORT).show()
                    }
                },
            ) {
                Text(stringResource(R.string.register_button_text))
            }
            Button(onClick = { onNavigateToLoginScreen() }, modifier = Modifier.padding(horizontal = 10.dp)) {
                Text("Go Back")
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