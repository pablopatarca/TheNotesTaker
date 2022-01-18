package app.pablopatarca.thenotestaker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.pablopatarca.thenotestaker.ui.edit.EditNoteUIScreen
import app.pablopatarca.thenotestaker.ui.main.MainUIScreen
import app.pablopatarca.thenotestaker.ui.main.NotesViewModel
import app.pablopatarca.thenotestaker.ui.theme.TheNotesTakerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NotesViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TheNotesTakerTheme {

                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route
                    ) {
                        composable(route = Screen.NoteScreen.route) {
                            MainUIScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable(
                            route = Screen.EditScreen.route + "?id={id}",
                            arguments = listOf(
                                navArgument("id"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
//                                navArgument("noteColor"){
//                                    type = NavType.IntType
//                                    defaultValue = -1
//                                }
                            )
                        ) {
                            EditNoteUIScreen(
                                navController = navController
                            )
                        }
                    }
                }

            }
        }

//        setContentView(R.layout.main_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
    }
}