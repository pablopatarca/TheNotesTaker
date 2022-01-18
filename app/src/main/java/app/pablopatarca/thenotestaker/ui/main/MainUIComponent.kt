package app.pablopatarca.thenotestaker.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.pablopatarca.thenotestaker.ui.Screen

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainUIComponent(
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
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp, 8.dp, 8.dp),
                horizontalArrangement =  Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Notes",
                    style = MaterialTheme.typography.h4
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
    state: NotesUIState,
    navController: NavController
){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(state.notes){ note ->
            NoteItem(
                note = note,
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            Screen.EditScreen.route + "?id=${note.id}"
                        )
                    }
            )
        }
    }
}