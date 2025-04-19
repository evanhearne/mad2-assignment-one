package ie.setu.mad2_assignment_one.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ie.setu.mad2_assignment_one.R
import ie.setu.mad2_assignment_one.navigation.About
import ie.setu.mad2_assignment_one.navigation.Main
import ie.setu.mad2_assignment_one.navigation.Account
import ie.setu.mad2_assignment_one.navigation.TopLevelRoute

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    val topLevelRoutes = listOf(
        TopLevelRoute("Shopping", Main ,Icons.Filled.ShoppingCart),
        TopLevelRoute("Account", Account, Icons.Filled.Person),
        TopLevelRoute("About", About, Icons.Filled.Info)
    )
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            topLevelRoutes.forEachIndexed { index, topLevelRoute ->
                val isSelected = selectedOption == index

                Box(
                    modifier = Modifier
                        .weight(1f) // Ensures equal space
                        .aspectRatio(1.6f) // Makes it a square
                        .clickable {
                            onOptionSelected(index)
                            navController.navigate(topLevelRoute.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.secondary
                            else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = topLevelRoute.icon,
                            contentDescription = stringResource(id = when(index) {
                                0 -> R.string.shopping_list_icon
                                1 -> R.string.account_icon
                                else -> R.string.about_icon
                            })
                        )
                        Text(text = topLevelRoute.name)
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewBottomAppBar() {
    BottomNavigationBar(navController = NavController(context = LocalContext.current), selectedOption =0, onOptionSelected = {})
}