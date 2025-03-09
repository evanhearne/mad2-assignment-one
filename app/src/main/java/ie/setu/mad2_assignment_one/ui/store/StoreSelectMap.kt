package ie.setu.mad2_assignment_one.ui.store

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import ie.setu.mad2_assignment_one.data.loadStores
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState

@Composable
fun StoreSelectMap(context: Context, onChooseStore: (Any?) -> Unit) {
    val stores = loadStores(context)

    val cameraPositionState = rememberCameraPositionState {
        if (stores.isNotEmpty()) {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                LatLng(stores[0].latitude, stores[0].longitude), 12f
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        stores.forEach { store ->
            MarkerInfoWindow(
                state = MarkerState(LatLng(store.latitude, store.longitude)),
                onInfoWindowClick = { onChooseStore(store) }
            ) {
                Card {
                    Column(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)) {
                        Row(modifier = Modifier
                            .align(Alignment.CenterHorizontally)) {
                            Text(store.name, textAlign = TextAlign.Center)
                        }
                        Row(modifier = Modifier
                            .align(Alignment.CenterHorizontally)) {
                            Text(store.phoneNumber, textAlign = TextAlign.Center)
                        }
                        Row(modifier = Modifier
                            .align(Alignment.CenterHorizontally)) {
                            Text(store.address, textAlign = TextAlign.Center)
                        }
                        store.openingHours.forEach { openingHour ->
                            Row(modifier = Modifier
                                .align(Alignment.CenterHorizontally)) {
                                Text(openingHour, textAlign = TextAlign.Center)
                            }
                        }
                        Row(modifier = Modifier
                            .align(Alignment.CenterHorizontally)) {
                            Text("Tap me to choose store")
                        }
                    }
                }
            }
        }
    }
}