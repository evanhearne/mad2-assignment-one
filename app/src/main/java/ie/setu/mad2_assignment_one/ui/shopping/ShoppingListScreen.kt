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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier, onNavigateBack: () -> Unit, shoppingListViewModel: ShoppingListViewModel, onItemClick: (ShoppingItem) -> Unit) {
    var total: Double
    Column {
        Row {
            TopAppBar(
                title = { Text("Shopping List") },
                modifier = modifier,
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Button")
                    }
                                 },
            )
        }
        LazyColumn(modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 100.dp, bottom = 25.dp)) {
            val shoppingList = shoppingListViewModel.shoppingList
            if (shoppingList.isEmpty()) {
                item {
                    Text("You have no items in your shopping list. ")
                }
            } else {
                total = 0.00 // reset price when update list
                for (item in shoppingList) {
                    item {
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
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "${item.shoppingItem.name} Image",
                                    modifier
                                        .padding(5.dp)
                                        .size(50.dp)
                                )
                                Text(item.shoppingItem.name, modifier.padding(8.dp), fontSize = 20.sp)
                            }
                            Row(modifier = modifier.align(Alignment.CenterHorizontally).padding(bottom = 6.dp)) {
                                Text(
                                    "x ${item.quantity}",
                                    fontSize = 20.sp
                                )
                                total += item.shoppingItem.price * item.quantity
                                Text("   @ ${item.shoppingItem.price} ea ", fontSize = 20.sp)
                            }
                            Row (modifier = modifier
                                .align(Alignment.CenterHorizontally)){
                                FilledIconButton(
                                    onClick = { shoppingListViewModel.decreaseItemQuantity(item) },
                                    modifier = modifier,
                                    enabled = true,
                                ) {
                                    Text("—", fontSize = 20.sp)
                                }
                                FilledIconButton(
                                    onClick = { shoppingListViewModel.increaseItemQuantity(item) },
                                    modifier = modifier,
                                    enabled = true,
                                ) {
                                    Text("+", fontSize = 20.sp)
                                }
                            }
                        }
                    }
                }
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
                            onClick = {shoppingListViewModel.removeAllItems()},
                            modifier = modifier,
                            enabled = true,
                        ) {
                            Text("Remove All Items")
                        }
                    }

                }
            }
            }
        }
    }

@Preview
@Composable
fun ShoppingListScreenPreview() {
    //hoppingListScreen()
}