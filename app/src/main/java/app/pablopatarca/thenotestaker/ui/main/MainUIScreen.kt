package app.pablopatarca.thenotestaker.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import app.pablopatarca.thenotestaker.ui.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainUIScreen(
    viewModel: NotesViewModel,
    navController: NavController
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
                IconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.snackbarHostState
                                .showSnackbar("Snackbar #")
                        }
                    }
                ) {
                    Icon(Icons.Filled.MoreHoriz, contentDescription = "more")
                }
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NotesList(
                state = state,
                navController = navController,
                viewModel = viewModel
            )
        }

        scope.launch {
            viewModel.messages.collectLatest {
                if(it.undo){
                    val result = scaffoldState.snackbarHostState
                        .showSnackbar(it.message, "Undo")
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.undoDelete()
                    }
                } else
                    scaffoldState.snackbarHostState
                        .showSnackbar(it.message)
            }
        }

    }
}

@Composable
fun NotesList(
    state: NotesState,
    navController: NavController,
    viewModel: NotesViewModel
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
                onLongClick = {
                    note.id?.let { viewModel.deleteNote(it) }
                }
            )
        }
    }
}