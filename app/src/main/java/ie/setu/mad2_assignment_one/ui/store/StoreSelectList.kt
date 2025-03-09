package ie.setu.mad2_assignment_one.ui.store

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.mad2_assignment_one.data.Store
import ie.setu.mad2_assignment_one.data.loadStores

@Composable
fun StoreSelectList(context: Context, onClickStore: (Any?) -> Unit) {
    val stores = loadStores(context)

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(stores) { store ->
            StoreItem(store, onClickStore)
        }
    }
}

@Composable
fun StoreItem(store: Store, onClickStore: (Any?) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = store.name, style = MaterialTheme.typography.titleLarge)
            Text(text = store.address, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Phone: ${store.phoneNumber}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Opening Hours:")
            store.openingHours.forEach{ openingHour ->
                Text(openingHour)
            }
            Button(onClick = {onClickStore(store)}) {
                Text("Choose Store")
            }
        }
    }
}
