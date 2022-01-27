package app.pablopatarca.thenotestaker.ui.drawer

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
import app.pablopatarca.thenotestaker.domain.Tag
import app.pablopatarca.thenotestaker.ui.main.NotesState

@Composable
fun TagsComponentScreen(
    state: NotesState,
    onClick: (Long) -> Unit
){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(state.tags){ tag ->
            NotebookItem(
                tag = tag,
                onClick = onClick
            )
        }
    }
}

@Composable
fun NotebookItem(
    tag: Tag,
    onClick: (Long)-> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                tag.id?.let(onClick)
            }
    ) {
        Text(
            text = tag.name,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}