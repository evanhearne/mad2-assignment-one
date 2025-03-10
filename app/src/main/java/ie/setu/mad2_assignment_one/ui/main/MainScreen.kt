package ie.setu.mad2_assignment_one.ui.main

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.mad2_assignment_one.R
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.loadShoppingItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToShoppingList: () -> Unit,
    onItemClick: (Any?) -> Unit,
    query: MutableState<String>,
    scrollState: ScrollState,
    items: List<ShoppingItem>,
    onNavigatetoChooseStore: () -> Unit
) {
        Column(modifier.verticalScroll(scrollState).padding(bottom = 50.dp)) {
            Row {
                // Top App Bar for Home Screen
                TopAppBar(
                    title = { Text(stringResource(R.string.main_screen_top_bar_title)) },
                    modifier = modifier,
                    actions = {
                        // Choose Store Button
                        Button(
                            onClick = { onNavigatetoChooseStore() }, // needs to be defined for store selection in later iteration.
                            modifier = modifier,
                            enabled = true,
                        ) {
                            Icon(
                                Icons.Filled.Place,
                                contentDescription = stringResource(R.string.choose_store_button_content_description)
                            )
                            Text(stringResource(R.string.choose_store_button_text))
                        }
                    }
                )
            }
            // App Logo
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 100.dp)
            ) {
                Image(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = stringResource(R.string.besco_logo_content_description),
                    modifier = modifier.size(100.dp)
                )
            }
            // Welcome text // Title
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.main_screen_welcome_text),
                    modifier = modifier,
                    fontSize = 26.sp,
                )
            }
            // Search Bar
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            ) {
                SearchBar(query = query.value, onQueryChange = { query.value = it })
            }
            // View Shopping List Button
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                Button(
                    onClick = onNavigateToShoppingList,
                    modifier = modifier,
                    enabled = true,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.List,
                        stringResource(R.string.shopping_cart_image_content_description)
                    )
                    Text(stringResource(R.string.view_shopping_list_button_text))
                }
            }
            // Animation to prompt swipe down.

            // Smooth up and down animation to prompt user to swipe to view list.
            // https://developer.android.com/develop/ui/compose/animation/quick-guide#repeat-animation
            // https://developer.android.com/develop/ui/compose/animation/quick-guide#animate-text-scale
            val transition = rememberInfiniteTransition()

            // Animate Y offset smoothly
            val offsetY by transition.animateFloat(
                initialValue = -20f,
                targetValue = 0f,  // Maximum distance of movement
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 700, easing = FastOutSlowInEasing
                        // https://developer.android.com/develop/ui/compose/animation/customize#animationspec
                    ),
                    repeatMode = RepeatMode.Reverse // Moves up and down smoothly
                )
            )

            // Swipe up ^
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Down Arrow",
                    modifier = modifier
                        .size(40.dp)
                        .offset(y = offsetY.dp)
                )
            }
            Row(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = offsetY.dp)
            ) {
                Text(stringResource(R.string.main_screen_user_swipe_prompt), fontSize = 26.sp)
            }
            ScrollableGrid(onItemClick = onItemClick, items = items)
        }
}

@Composable
fun SearchBar(query:String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.main_screen_search_bar_placeholder)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_icon_content_description)) },
        singleLine = true
    )
}

// Returns a scrollable grid containing all items in store.
// User can click on an item to view further information about it.
@Composable
fun ScrollableGrid(
    modifier: Modifier = Modifier,
    onItemClick: (ShoppingItem) -> Unit,
    items: List<ShoppingItem>
) {
    for (item in items.indices step 2) { // Step 2 to process items in pairs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // First item in the row (always exists)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { onItemClick(items[item]) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp)
                    ) {
                        Image(
                            imageVector = Icons.Default.Star,
                            contentDescription = "${items[item].name} image",
                            modifier = modifier.size(50.dp)
                        )
                    }
                    Row(
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = items[item].name,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Second item in the row (only if it exists)
            if (item + 1 < items.size) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { onItemClick(items[item + 1]) },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp)
                        ) {
                            Image(
                                imageVector = Icons.Default.Star,
                                contentDescription = "${items[item + 1].name} image",
                                modifier = modifier.size(50.dp)
                            )
                        }
                        Row(
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = items[item + 1].name,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                // If there's an odd item count, add empty space to keep layout balanced
                // https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/package-summary#Spacer(androidx.compose.ui.Modifier)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

// Preview for main screen
@Preview
@Composable
fun PreviewMainScreen(){
    MainScreen(
        onNavigateToShoppingList = {},
        onItemClick = {},
        query = remember { mutableStateOf("") },
        scrollState = rememberScrollState(),
        items = loadShoppingItems(LocalContext.current),
        onNavigatetoChooseStore = {}
    )
}

// Preview for search bar
@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        "",
        onQueryChange = {}
    )
}

// Preview for scrollable grid
@Preview
@Composable
fun ScrollableGridPreview(){
    ScrollableGrid(
        onItemClick = {},
        items = loadShoppingItems(LocalContext.current)
    )
}