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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ie.setu.mad2_assignment_one.R
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.ShoppingListItem
import ie.setu.mad2_assignment_one.data.repository.ShoppingListItemListsRepository
import ie.setu.mad2_assignment_one.ui.theme.itemAvailableBackgroundColor
import ie.setu.mad2_assignment_one.ui.theme.itemAvailableColor
import ie.setu.mad2_assignment_one.ui.theme.itemUnavailableBackgroundColor
import ie.setu.mad2_assignment_one.ui.theme.itemUnavailableColor
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel
import kotlinx.coroutines.launch
import ie.setu.mad2_assignment_one.data.Category

// Item Details Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(modifier: Modifier = Modifier, onNavigateBack: () -> Unit, item: ShoppingItem, shoppingListViewModel: ShoppingListViewModel) {
    val scope = rememberCoroutineScope()
    Column {
        Row {
            // Top App Bar for Item Details Screen
            TopAppBar(
                title = { Text(stringResource(R.string.item_details_screen_top_app_bar_title)) },
                modifier = modifier,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_content_description))

                    }
                                 },
            )
        }
        // Item Details Card
        Row (modifier= modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            Card(modifier = modifier
                .fillMaxWidth()
                .padding(15.dp)) {
                // Item Image
                Image(imageVector = Icons.Default.Star, contentDescription = stringResource(R.string.item_image_content_description), Modifier
                    .size(110.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp))
                // Item Name
                Text(item.name, modifier = modifier.align(Alignment.CenterHorizontally), fontSize = 30.sp)
                // Item Price
                Text("${item.price}", modifier = modifier.align(Alignment.CenterHorizontally), fontSize = 25.sp)
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