package app.pablopatarca.thenotestaker.ui.notebooks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.pablopatarca.thenotestaker.domain.Notebook
import app.pablopatarca.thenotestaker.ui.Screen
import app.pablopatarca.thenotestaker.ui.main.NotesUIState

@Composable
fun NotebooksScreen(
    state: NotesUIState,
    navController: NavController
){
    NotesList(state = state, navController = navController)
}

@Composable
fun NotesList(
    state: NotesUIState,
    navController: NavController
){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(state.notebooks){ notebook ->
            NotebookItem(
                notebook = notebook,
                onClick = {
                    navController.navigate(
                        Screen.NotesScreen.route
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun NotebookItem(
    notebook: Notebook,
    onClick: ()-> Unit = {},
    modifier: Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Text(
            text = notebook.name,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}