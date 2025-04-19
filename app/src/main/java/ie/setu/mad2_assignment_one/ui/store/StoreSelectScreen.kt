package ie.setu.mad2_assignment_one.ui.store

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ie.setu.mad2_assignment_one.ui.BottomNavigationBar

// Store Selection Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreSelectScreen(context: Context, onNavigateBack: () -> Unit, onChooseStore: (Any?) -> Unit, bottomNavBar: @Composable () -> Unit) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("List", "Map")
    Scaffold(topBar = {TopAppBar(
        title = { Text("Select Store") },
        navigationIcon = {
            IconButton(
                onClick = { onNavigateBack() },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back to Home"
                )
            }
        },
    )}, bottomBar = { bottomNavBar() }) { padding ->
        Column(Modifier.padding(padding)) {
            Row {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
            Row {
                when (selectedTabIndex) {
                    0 -> StoreSelectList(
                        context,  onChooseStore) // Replace with your list screen composable
                    1 -> StoreSelectMap(context, onChooseStore )
                }
            }
        }
    }

}

// Store Selection Screen Preview
@Preview
@Composable
fun StoreSelectScreenPreview() {
    StoreSelectScreen(LocalContext.current, {}, {}, bottomNavBar = { BottomNavigationBar(navController = NavController(LocalContext.current), onOptionSelected = {}, selectedOption = 0) })
}
