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
import kotlin.math.round
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier, onNavigateBack: () -> Unit, onItemClick: () -> Unit) {
    var value: Double
    var total = 0.00
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
            .padding(bottom = 25.dp)) {
            items(20) { item ->
                Card(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { },
                    onClick = onItemClick
                ) {
                    Row(
                        modifier
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping List Item $item",
                            modifier
                                .padding(5.dp)
                                .size(50.dp)
                        )
                        Text("Item $item", modifier.padding(8.dp), fontSize = 20.sp)
                        Text(
                            "x ${(Math.random() * 10).roundToInt()}",
                            modifier.padding(8.dp),
                            fontSize = 20.sp
                        )
                        value = round(Math.random() * 1000) / 100
                        total += value
                        Text("@ $value ea ", fontSize = 20.sp)
                        FilledIconButton(
                            onClick = {},
                            modifier = modifier,
                            enabled = true,
                        ) {
                            Text("—", fontSize = 20.sp)
                        }
                        FilledIconButton(
                            onClick = {},
                            modifier = modifier,
                            enabled = true,
                        ) {
                            Text("+", fontSize = 20.sp)
                        }
                    }
                }
            }
            item {
                Box {
                    Box (modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp)){
                        Text(text = "Total -> €${total.roundToInt()}.55", fontSize = 25.sp)
                    }
                    Box (modifier
                        .fillMaxWidth()
                        .padding(end = 15.dp) ,contentAlignment = Alignment.BottomEnd) {
                        Button(
                            onClick = {},
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