package ie.setu.mad2_assignment_one.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ie.setu.mad2_assignment_one.ui.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    bottomNavBar: @Composable () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val topBar = @Composable { TopAppBar(title = { Text("Account") }) }
    Scaffold(
        topBar = { topBar() },
        bottomBar = { bottomNavBar() })
    { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize(), verticalArrangement = Arrangement.Center) {
            var user by remember { mutableStateOf(Firebase.auth.currentUser) }
            if (user != null) {
                // check if user logged in
                // User Photo
                Row(modifier = Modifier
                    .align(Alignment.CenterHorizontally)) {
                    AsyncImage(user!!.photoUrl, contentDescription = "${user!!.displayName} profile picture ", modifier = Modifier.size(250.dp).clip(
                        CircleShape
                    ))
                }
                // Display name
                Row(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)) {
                    Text(text ="Hello ${user!!.displayName} ! ", fontSize = 25.sp)
                }
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Button(onClick = {
                        Firebase.auth.signOut()
                        user = null // refresh screen
                    }) {
                        Text("Log Out")
                    }
                }
            } else {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    LoginScreen(modifier = Modifier, context = LocalContext.current, onNavigateToHome = { onNavigateToHome() })
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAccountScreen() {
    AccountScreen(bottomNavBar = { BottomNavigationBar(navController = NavController(context = LocalContext.current), selectedOption = 1, onOptionSelected = {}) }, onNavigateToHome = {})
}