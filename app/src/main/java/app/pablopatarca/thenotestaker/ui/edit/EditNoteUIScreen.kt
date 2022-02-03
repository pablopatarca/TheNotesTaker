package app.pablopatarca.thenotestaker.ui.edit

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.pablopatarca.thenotestaker.R
import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun EditNoteUIScreen(
    navController: NavController,
    noteColor: Int = -1,
    viewModel: EditNoteViewModel = hiltViewModel()
){

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val tagsState = viewModel.noteTags.value
    val scaffoldState = rememberScaffoldState()
    val selectedNoteColor = remember {
        Animatable(
            Color(
                if (noteColor!=-1) noteColor else viewModel.noteColor.value
            )
        )
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveNote()
                    navController.navigateUp()
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(selectedNoteColor.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp, 8.dp, 8.dp),
                horizontalArrangement =  Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.edit_note_screen_title),
                    style = Typography.h4.copy(fontWeight = FontWeight.Bold)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Note.noteColors.forEach {
                    val color = it.toArgb()
                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .background(it)
                        .border(
                            width = 2.dp,
                            color = if (viewModel.noteColor.value == color) Color.Black
                            else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                selectedNoteColor.animateTo(
                                    targetValue = Color(color),
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                            }
                            viewModel.setColor(color)
                        }
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            TextField(
                value = titleState,
                onValueChange = {
                    viewModel.enteredTitle(it)
                },
                label = { Text(text = stringResource(id = R.string.note_title_label)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = contentState,
                onValueChange = {
                    viewModel.enteredContent(it)
                },
                label = { Text(text = stringResource(id = R.string.note_content_label)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray
                ),
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = tagsState,
                onValueChange = {
                    viewModel.enteredTags(it)
                },
                label = { Text(text = stringResource(id = R.string.note_tags_label)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}