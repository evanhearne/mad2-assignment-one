package ie.setu.mad2_assignment_one.ui.main

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column (modifier = modifier
        .verticalScroll(rememberScrollState())) {
        Row {
            TopAppBar(
                title = { Text("Home") },
                modifier = modifier,
                actions = {
                    Button(
                        onClick = {},
                        modifier = modifier,
                        enabled = true,
                    ) {
                        Icon(Icons.Filled.Place, contentDescription = "Choose Store")
                        Text("  Choose Store")
                    }
                }
            )
        }
        Row (modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 100.dp)) {
            Image(imageVector = Icons.Outlined.ShoppingCart, contentDescription = "Shopping Cart Image", modifier = modifier.size(100.dp))
        }
        Row (modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 10.dp, bottom = 10.dp)) {
            Text(
                text = "Welcome to Besco!",
                modifier = modifier,
                fontSize = 26.sp,
            )
        }
        Row (modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 10.dp)) {
            SearchBar("") { }
        }
        Row (modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 25.dp)) {
            Button(
                onClick = {},
                modifier = modifier,
                enabled = true,
            ) {
                Icon(Icons.AutoMirrored.Filled.List, "Shopping Cart Image")
                Text(" View Shopping List")
            }
        }

        // Smooth up and down animation to prompt user to swipe to view list.
        // https://developer.android.com/develop/ui/compose/animation/quick-guide#repeat-animation
        // https://developer.android.com/develop/ui/compose/animation/quick-guide#animate-text-scale
        val transition = rememberInfiniteTransition()

        // Animate Y offset smoothly
        val offsetY by transition.animateFloat(
            initialValue = -20f,
            targetValue = 0f,  // Maximum distance of movement
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 700, easing = FastOutSlowInEasing
                // https://developer.android.com/develop/ui/compose/animation/customize#animationspec
                ),
                repeatMode = RepeatMode.Reverse // Moves up and down smoothly
            )
        )
        Row (modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 25.dp)) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowUp, contentDescription = "Down Arrow", modifier = modifier
                .size(40.dp)
                .offset(y = offsetY.dp))
        }
        Row(modifier = modifier
            .align(Alignment.CenterHorizontally)
            .offset(y = offsetY.dp)) {
            Text("Swipe up to view all stock", fontSize = 26.sp)
        }
        ScrollableGrid()
    }
}

@Composable
fun SearchBar(query:String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search for your items...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        singleLine = true
    )
}

@Composable
fun ScrollableGrid(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(top=75.dp, bottom = 50.dp)) {
        for (item in 1..10 step 2) { // step 2 to skip every second item
            Row(
                modifier = Modifier.fillMaxWidth(), // Ensure the row takes up full width
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between items
            ) {
                // First item in the row
                Column(
                    modifier = Modifier.weight(1f) // Each item takes up 50% of the row
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row (modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp)) {
                            Image(imageVector = Icons.Default.Star, contentDescription = "Item $item", modifier = modifier.size(50.dp))
                        }
                        Row (modifier = modifier
                            .align(Alignment.CenterHorizontally)) {
                            Text(
                                text = "Item $item",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        
                    }
                }

                // Second item in the row
                if (item + 1 <= 10) { // Check if there is a second item
                    Column(
                        modifier = Modifier.weight(1f) // Each item takes up 50% of the row
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row (modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp)) {
                                Image(imageVector = Icons.Default.Star, contentDescription = "Item ${item + 1}", modifier = modifier.size(50.dp))
                            }
                            Row (modifier = modifier
                                .align(Alignment.CenterHorizontally)) {
                                Text(
                                    text = "Item ${item + 1}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen(){
    MainScreen()
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        "",
        onQueryChange = {}
    )
}

@Preview
@Composable
fun ScrollableGridPreview(){
    ScrollableGrid()
}