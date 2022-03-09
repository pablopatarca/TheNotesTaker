package app.pablopatarca.thenotestaker.ui.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ModalBottomMenu(
    sheetContent: @Composable ColumnScope.() -> Unit
){
    var confirmStateChange by remember { mutableStateOf(false) }

    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        confirmStateChange = { confirmStateChange }
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = sheetContent
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.toggleable(
                    value = confirmStateChange,
                    role = Role.Checkbox,
                    onValueChange = { checked -> confirmStateChange = checked }
                )
            ) {
                Checkbox(checked = confirmStateChange, onCheckedChange = null)
                Spacer(Modifier.width(16.dp))
                Text("Skip Half Expanded State")
            }
            Spacer(Modifier.height(20.dp))
            Button(onClick = { scope.launch { state.show() } }) {
                Text("Click to show sheet")
            }
        }
    }
}