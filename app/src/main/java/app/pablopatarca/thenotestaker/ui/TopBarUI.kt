package app.pablopatarca.thenotestaker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import app.pablopatarca.thenotestaker.R
import app.pablopatarca.thenotestaker.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun TopBarUI(
    scaffoldState: ScaffoldState,
    onSearchClick: () -> Unit = {}
){
    val menuSelection = remember { mutableStateOf(MenuSelection.NONE) }
    val expandedMain = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.main_screen_title),
                style = Typography.h5.copy(fontWeight = FontWeight.Bold)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ){
                Icon(Icons.Filled.Menu, null)
            }
        },
        actions = {
            IconButton(
                onClick = onSearchClick
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }

            Box(
                Modifier.wrapContentSize(Alignment.TopEnd)
            ){
                IconButton(
                    onClick = {
                        expandedMain.value = true
                    }
                ) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More")
                }
            }
            MainMenu(
                menuSelection = menuSelection,
                expandedMain = expandedMain
            )
        }
    )
}

enum class MenuSelection {
    NONE,
    SETTINGS,
    ABOUT
}

@Composable
fun MainMenu(
    menuSelection: MutableState<MenuSelection>,
    expandedMain: MutableState<Boolean>
) {

    DropdownMenu(
        expanded = expandedMain.value,
        onDismissRequest = { expandedMain.value = false },
    ) {

        DropdownMenuItem(
            onClick = {
                // close main menu
                expandedMain.value = false
                menuSelection.value = MenuSelection.SETTINGS
            }
        ) {
            Text("Settings")
        }
        Divider()
        DropdownMenuItem(
            onClick = {
                // close main menu
                expandedMain.value = false
                menuSelection.value = MenuSelection.ABOUT
            }
        ) {
            Text("About")
        }
    }
}