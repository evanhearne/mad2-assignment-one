package ie.setu.mad2_assignment_one.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.mad2_assignment_one.R

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {}
                ) {
                    Column(horizontalAlignment = (Alignment.CenterHorizontally)) {
                        Row {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = stringResource(R.string.shopping_list_icon)
                            )
                        }
                        Row {
                            Text(
                                text = "Shopping"
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {}
                ) {
                    Column(horizontalAlignment = (Alignment.CenterHorizontally)) {
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = stringResource(R.string.account_icon)
                            )
                        }
                        Row {
                            Text(
                                text = "Account"
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {}
                ) {
                    Column(horizontalAlignment = (Alignment.CenterHorizontally)) {
                        Row {
                            Icon(imageVector = Icons.Filled.Info, contentDescription = stringResource(
                                R.string.about_icon
                            ))
                        }
                        Row {
                            Text(
                                text = "About"
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewBottomAppBar() {
    BottomNavigationBar(navController = NavController(context = LocalContext.current))
}