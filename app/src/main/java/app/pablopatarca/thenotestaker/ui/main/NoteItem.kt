package app.pablopatarca.thenotestaker.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.pablopatarca.thenotestaker.domain.Note
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteItem(
    note: Note,
    onClick: ()-> Unit = {},
    onLongClick: ()-> Unit = {}
){
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit){
                detectTapGestures(onLongPress = { onLongClick() })
            }
            .clickable {
                onClick.invoke()
            },
        backgroundColor = Color(
            if (note.color!=0) note.color
            else Note.noteColors.first().toArgb()
        ),
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "tags: ${note.tags.joinToString(", "){ it.name }}",
                    textAlign = TextAlign.Left,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.body2.copy(fontSize = 10.sp)
                )
                Text(
                    text = "Updated: ${note.updatedAt.toTime()}",
                    textAlign = TextAlign.Right,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body2.copy(fontSize = 10.sp)
                )
            }
        }
    }

}

private fun Long.toTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}

@Preview
@Composable
fun NoteItem(){
    NoteItem(
        note = Note(
            title = "Preview",
            content = "Preview",
            createdAt = 0,
            updatedAt = 0,
            color = 0
        ),
        onClick = {},
        onLongClick = {}
    )
}