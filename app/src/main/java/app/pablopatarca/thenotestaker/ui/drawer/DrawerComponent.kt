package app.pablopatarca.thenotestaker.ui.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.pablopatarca.thenotestaker.domain.Tag
import app.pablopatarca.thenotestaker.ui.main.NotesState

@Composable
fun DrawerComponent(
    state: NotesState,
    onTagClicked: (Long) -> Unit,
    content: @Composable () -> Unit
){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val currentState = remember { mutableStateOf(state.tags.firstOrNull()) }
    val coroutineScope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {

            TagsComponentScreen(
                state = state,
                onClick = onTagClicked
            )
//            DrawerContentComponent(
//                state = state,
//                selected = currentState,
//                closeDrawer = {
//                    coroutineScope.launch { drawerState.close() }
//                }
//            )
        },
        content = content
    )
}

@Composable
fun DrawerContentComponent(
    state: NotesState,
    selected: MutableState<Tag>,
    closeDrawer: () -> Unit
){

    Column(modifier = Modifier.fillMaxSize()) {

        state.tags.forEach { tag ->

            Column(
                modifier = Modifier.clickable(
                    onClick = {
                        selected.value = tag
                        closeDrawer()
                    }
                ),
                content = {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = if (selected.value == tag) {
                            MaterialTheme.colors.secondary
                        } else {
                            MaterialTheme.colors.surface
                        }
                    ) {
                        Text(text = tag.name, modifier = Modifier.padding(16.dp))
                    }
                }
            )
        }
    }

}