package ie.setu.mad2_assignment_one.ui.shopping
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ie.setu.mad2_assignment_one.R
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier, onNavigateBack: () -> Unit, shoppingListViewModel: ShoppingListViewModel, onItemClick: (ShoppingItem) -> Unit) {
    val scope = rememberCoroutineScope()
    // total variable is used to display total at the end of the shopping list
    // it is calculated on the fly.
    var total: Double
    // Top App Bar
    Column {
        Row {
            TopAppBar(
                title = { Text(stringResource(R.string.shopping_list_screen_top_app_bar_title)) },
                modifier = modifier,
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(
                            R.string.back_button_content_description
                        )
                        )
                    }
                                 },
            )
        }
        // List generation begins here...
        LazyColumn(modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 100.dp, bottom = 25.dp)) {
            // load in view model for shopping list
            val shoppingList = shoppingListViewModel.shoppingList
            // if there are no shopping items the list is empty.
            // the user receives a text letting them know this.
            if (shoppingList.isEmpty()) {
                item {
                    Text(stringResource(R.string.shopping_list_empty))
                }
            } else {
                // otherwise if there are items it will begin list generation...
                total = 0.00 // reset price when update list
                // iterate through all items in the shopping list
                for (item in shoppingList) {
                    item {
                        // shopping list item card
                        Card(
                            modifier = modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable { onItemClick(item.shoppingItem) },
                        ) {
                            Row(
                                modifier
                                    .align(Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Shopping List Item Image
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "${item.shoppingItem.name} Image",
                                    modifier
                                        .padding(5.dp)
                                        .size(50.dp)
                                )
                                // Shopping list item name
                                Text(item.shoppingItem.name, modifier.padding(8.dp), fontSize = 20.sp)
                            }
                            Row(modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 6.dp)) {
                                // Item Quantity
                                Text(
                                    "x ${item.quantity}",
                                    fontSize = 20.sp
                                )
                                // Calculate total by adding the item price * quantity to total var...
                                total += item.shoppingItem.price * item.quantity
                                // Item Price
                                Text("   @ ${item.shoppingItem.price} ea ", fontSize = 20.sp)
                            }
                            Row (modifier = modifier
                                .align(Alignment.CenterHorizontally)){
                                // Decrement Item Quantity
                                FilledIconButton(
                                    onClick = {
                                        scope.launch {
                                            val email = Firebase.auth.currentUser?.email
                                            if (email != null)
                                                shoppingListViewModel.decreaseItemQuantity(item, email)
                                            else
                                                shoppingListViewModel.decreaseItemQuantity(item)
                                        }
                                    },
                                    modifier = modifier,
                                    enabled = true,
                                ) {
                                    Text("—", fontSize = 20.sp)
                                }
                                // Increment Item Quantity
                                FilledIconButton(
                                    onClick = {
                                        scope.launch {
                                            val email = Firebase.auth.currentUser?.email
                                            if (email != null)
                                                shoppingListViewModel.increaseItemQuantity(item, email)
                                            else
                                                shoppingListViewModel.increaseItemQuantity(item)
                                        }
                                    },
                                    modifier = modifier,
                                    enabled = true,
                                ) {
                                    Text("+", fontSize = 20.sp)
                                }
                            }
                        }
                    }
                }
                // Total + Clear All Button
                item {
                    Box {
                        Box (modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 10.dp)){
                            Text(text = "Total -> €${"%.2f".format(total)}", fontSize = 25.sp) // format total to 2dp as weird 3 decimal place observed for certain additions
                        }
                        }
                    Box (modifier
                        .fillMaxWidth()
                        .padding(end = 15.dp) ,contentAlignment = Alignment.BottomEnd) {
                        Button(
                            onClick = {
                                scope.launch {
                                    val email = Firebase.auth.currentUser?.email
                                    if (email != null)
                                        shoppingListViewModel.removeAllItems(email)
                                    else
                                        shoppingListViewModel.removeAllItems()
                                }
                            },
                            modifier = modifier,
                            enabled = true,
                        ) {
                            Text(stringResource(R.string.remove_all_items))
                        }
                    }

                }
            }
            }
        }
    }

// Preview for shopping List Screen
@Preview
@Composable
fun ShoppingListScreenPreview() {
    ShoppingListScreen(onNavigateBack = {}, onItemClick = {},
        shoppingListViewModel = ShoppingListViewModel()
    )
}