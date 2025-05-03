package ie.setu.mad2_assignment_one.ui

import androidx.compose.runtime.*
import coil3.compose.AsyncImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.mad2_assignment_one.R
import ie.setu.mad2_assignment_one.data.Category
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.ShoppingListItem
import ie.setu.mad2_assignment_one.data.repository.ShoppingListItemListsRepository
import ie.setu.mad2_assignment_one.ui.theme.itemAvailableBackgroundColor
import ie.setu.mad2_assignment_one.ui.theme.itemAvailableColor
import ie.setu.mad2_assignment_one.ui.theme.itemUnavailableBackgroundColor
import ie.setu.mad2_assignment_one.ui.theme.itemUnavailableColor
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    item: ShoppingItem,
    shoppingListViewModel: ShoppingListViewModel
) {
    val scope = rememberCoroutineScope()
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var imageError by remember { mutableStateOf<String?>(null) }

    // Run once when item.imageRes changes
    LaunchedEffect(item.imageRes) {
        try {
            // Ensure anonymous sign-in if not already signed in
            if (Firebase.auth.currentUser == null) {
                Firebase.auth.signInAnonymously().await()
            }

            // Get the download URL from Firebase Storage
            val ref = Firebase.storage.reference.child(item.imageRes)
            val url = ref.downloadUrl.await().toString()
            imageUrl = url
        } catch (e: Exception) {
            imageError = e.localizedMessage ?: "Error loading image"
        }
    }

    Column {
        TopAppBar(
            title = { Text(stringResource(R.string.item_details_screen_top_app_bar_title)) },
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_content_description)
                    )
                }
            }
        )

        Row(
            modifier = modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                when {
                    imageUrl != null -> {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "${item.name} image",
                            modifier = Modifier
                                .size(200.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp)
                        )
                    }
                    imageError != null -> {
                        Text(
                            text = imageError ?: "Error loading image",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    else -> {
                        Text(
                            text = "Loading image...",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

                // Item name
                Text(
                    item.name,
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    fontSize = 30.sp
                )
                // Item price
                Text(
                    "${item.price}",
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    fontSize = 25.sp
                )
                var color = itemAvailableBackgroundColor
                if (!item.availability)
                    color = itemUnavailableBackgroundColor
                // Item Availability
                Button(
                    onClick = {},
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp),
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(disabledContainerColor = color)
                ) {
                    // checks item's availability to show different icon as needed
                    if (item.availability) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.item_available_icon_content_description),
                            tint = itemAvailableColor
                        )
                        Text(stringResource(R.string.in_stock), color = itemAvailableColor)
                    } else {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = stringResource(R.string.item_unavailable_icon_content_description),
                            tint = itemUnavailableColor
                        )
                        Text(stringResource(R.string.out_of_stock), color = itemUnavailableColor)
                    }
                }
                // Item Category
                Text(item.category.name, textAlign = TextAlign.Center, modifier= modifier.fillMaxWidth().padding(15.dp))
                // Item Description
                Text(item.description, textAlign = TextAlign.Center, modifier = modifier
                    .padding(15.dp)
                    .fillMaxWidth())
                // Add to Shopping List Button
                // only shows if item is available
                if (item.availability) {
                    Button(
                        onClick = {
                                scope.launch {
                                    val email = Firebase.auth.currentUser?.email
                                    if (email != null)
                                        shoppingListViewModel.addItem(ShoppingListItem(0, item, 1), email)
                                    else
                                        shoppingListViewModel.addItem(ShoppingListItem(0, item, 1))
                                }
                        }, modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = stringResource(R.string.shopping_cart_logo)
                        )
                        Text(stringResource(R.string.add_to_shopping_list))
                    }
                }
            }
        }
    }
}

// Item Details Screen Preview
@Preview
@Composable
fun PreviewItemDetailsScreen() {
    ItemDetailsScreen(
        onNavigateBack = {},
        item = ShoppingItem(0, "", "AA", "aaa", 0.00, Category.GROCERIES, true),
        shoppingListViewModel = ShoppingListViewModel(
            shoppingListItemListsRepository = AppViewModelProvider.factory as ShoppingListItemListsRepository
        ),
    )
}