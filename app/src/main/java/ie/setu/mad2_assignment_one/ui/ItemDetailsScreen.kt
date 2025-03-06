package ie.setu.mad2_assignment_one.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(modifier: Modifier = Modifier, onNavigateBack: () -> Unit) {
    Column { 
        Row {
            TopAppBar(
                title = { Text("Item Details") },
                modifier = modifier,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Button")

                    }
                                 },
            )
        }
        Row (modifier= modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            Card(modifier = modifier.fillMaxWidth().padding(15.dp)) {
                Image(imageVector = Icons.Default.Star, contentDescription = "Item Image", Modifier
                    .size(110.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top=10.dp))
                Text("Item Name", modifier = modifier.align(Alignment.CenterHorizontally), fontSize = 30.sp)
                Text("€55.55", modifier = modifier.align(Alignment.CenterHorizontally), fontSize = 25.sp)
                Button(
                    onClick = {},
                    modifier = modifier.align(Alignment.CenterHorizontally).padding(top=10.dp),
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(disabledContainerColor = Color(0xFFCCFFCC))
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Green Check Mark", tint = Color(0xFF006400))
                    Text(" In Stock", color = Color(0xFF006400))
                }
                Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", textAlign = TextAlign.Center, modifier = modifier.padding(15.dp))
                Button(onClick = {}, modifier.align(Alignment.CenterHorizontally).padding(bottom = 10.dp)) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Shopping Cart Logo")
                    Text("  Add to Shopping List")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemDetailsScreen() {
    //ItemDetailsScreen()
}