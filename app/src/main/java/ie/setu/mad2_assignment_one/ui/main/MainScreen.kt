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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import ie.setu.mad2_assignment_one.R
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.loadShoppingItems
import ie.setu.mad2_assignment_one.ui.BottomNavigationBar
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToShoppingList: () -> Unit,
    onItemClick: (Any?) -> Unit,
    query: MutableState<String>,
    scrollState: ScrollState,
    items: List<ShoppingItem>,
    onNavigateChooseStore: () -> Unit,
    bottomNavBar: @Composable () -> Unit,
    chooseStoreText: String = LocalContext.current.getString(R.string.choose_store_button_text),
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val options = listOf("All") + items.map{ it.category.name }.distinct()
    var selectedOption by remember { mutableStateOf(options[0]) }
    var itemsToDisplay = remember { items.toMutableList() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.main_screen_top_bar_title)) },
                modifier = modifier,
                actions = {
                    // Filter drop down menu
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.more_options))
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Filter by category") },
                                onClick = { showDialog = true }
                            )
                        }
                    }
                    // Choose Store Button
                    Button(
                        onClick = { onNavigateChooseStore() }, // needs to be defined for store selection in later iteration.
                        modifier = modifier,
                        enabled = true,
                    ) {
                        Icon(
                            Icons.Filled.Place,
                            contentDescription = stringResource(R.string.choose_store_button_content_description)
                        )
                        Text(chooseStoreText)
                    }
                }
            )
        },
        bottomBar = { bottomNavBar() }
    ) { padding ->
        // Dialog Box to filter categories (not shown unless showDialog is toggled true) .
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(stringResource(R.string.choose_category_filter_string_prompt)) },
                text = {
                    Column {
                        options.forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedOption = option }
                                    .padding(vertical = 4.dp)
                            ) {
                                RadioButton(
                                    selected = (option == selectedOption),
                                    onClick = { selectedOption = option }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(option)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        itemsToDisplay = if (selectedOption != "All") {
                            items.filter{ it.category.name == selectedOption }.toMutableList()
                        } else {
                            items.toMutableList()
                        }
                    }) {
                        Text(stringResource(R.string.dialog_confirm))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(stringResource(R.string.dialog_cancel))
                    }
                }
            )
        }
        Column(modifier
            .verticalScroll(scrollState)
            .padding(bottom = 100.dp)) {
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
            ScrollableGrid(onItemClick = onItemClick, items = itemsToDisplay)
        }

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
    val storage = Firebase.storage

    for (item in items.indices step 2) { // Step 2 to process items in pairs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // First item in the row (always exists)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                var imageUrl1 = remember { mutableStateOf<String?>(null) }

                LaunchedEffect(items[item].imageRes) {
                    try {
                        val ref = storage.reference.child(items[item].imageRes)
                        imageUrl1.value = ref.downloadUrl.await().toString()
                    } catch (_: Exception) {
                        imageUrl1.value = null
                    }
                }

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
                        if (imageUrl1.value != null) {
                            AsyncImage(
                                model = imageUrl1.value,
                                contentDescription = "${items[item].name} image",
                                modifier = modifier.size(80.dp)
                            )
                        } else {
                            Image(
                                imageVector = Icons.Default.Star,
                                contentDescription = "${items[item].name} image",
                                modifier = modifier.size(50.dp)
                            )
                        }
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
                    var imageUrl2 = remember { mutableStateOf<String?>(null) }

                    LaunchedEffect(items[item + 1].imageRes) {
                        try {
                            val ref = storage.reference.child(items[item + 1].imageRes)
                            imageUrl2.value = ref.downloadUrl.await().toString()
                        } catch (_: Exception) {
                            imageUrl2.value = null
                        }
                    }

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
                            if (imageUrl2.value != null) {
                                AsyncImage(
                                    model = imageUrl2.value,
                                    contentDescription = "${items[item + 1].name} image",
                                    modifier = modifier.size(80.dp)
                                )
                            } else {
                                Image(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "${items[item + 1].name} image",
                                    modifier = modifier.size(50.dp)
                                )
                            }
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
        onNavigateChooseStore = {},
        bottomNavBar = { BottomNavigationBar(navController = NavController(LocalContext.current), selectedOption = 0, onOptionSelected = {}) }
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