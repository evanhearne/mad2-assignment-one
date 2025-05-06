package ie.setu.mad2_assignment_one.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(bottomNavBar: @Composable () -> Unit, onNavigateBack: () -> Unit){
    Scaffold(
        topBar = { TopAppBar(
            title = { Text("About") },
            navigationIcon = {
                IconButton(onClick = {
                    onNavigateBack()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back Arrow")
                }
            }
        ) },
        bottomBar = { bottomNavBar() },
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Row {
                // About Icon
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "About Screen Icon",
                    modifier = Modifier.size(100.dp)
                )
            }
            Row {
                Text("What is Besco ? ", fontSize = 35.sp)
            }
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    "Besco is a shopping app designed to help all your Besco needs and wants!",
                    textAlign = TextAlign.Center
                )
            }
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    "This app helps you look up stock in our stores, plan your shop, and calculate the total of your shop all in one go!",
                    textAlign = TextAlign.Center
                )
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Text(
                    "You will never have to guess again when it comes to shopping at Besco!",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewAboutScreen(){
    AboutScreen(
        bottomNavBar = { BottomNavigationBar(navController = NavController(LocalContext.current), selectedOption = 2, onOptionSelected = {}) },
        onNavigateBack = {},
    )
}