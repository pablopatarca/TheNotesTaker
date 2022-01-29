package app.pablopatarca.thenotestaker.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.pablopatarca.thenotestaker.ui.Screen

@Composable
fun MainUIScreen(
    viewModel: NotesViewModel,
    navController: NavController
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.EditScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                // Defaults to null, that is, No cutout
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.MoreHoriz, contentDescription = "more")
                }
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp, 8.dp, 8.dp),
                horizontalArrangement =  Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: Add a button action to filter by tag
                Text(
                    text = "Tags: ${state.tags.joinToString(", ") { it.name }}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp),
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.SansSerif
                )
            }

            NotesList(
                state = state,
                navController = navController
            )
        }

    }
}

@Composable
fun NotesList(
    state: NotesState,
    navController: NavController
){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(state.notes){ note ->
            NoteItem(
                note = note,
                onClick = {
                    navController.navigate(
                        Screen.EditScreen.route + "?id=${note.id}"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}