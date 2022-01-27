package app.pablopatarca.thenotestaker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.pablopatarca.thenotestaker.domain.NotesEvent
import app.pablopatarca.thenotestaker.domain.NotesFilter
import app.pablopatarca.thenotestaker.ui.drawer.DrawerComponent
import app.pablopatarca.thenotestaker.ui.drawer.TagsComponentScreen
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
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                Scaffold(
                    drawerContent = {
                        Text("The Note Taker", modifier = Modifier.padding(16.dp))
                        Divider()
                        TagsComponentScreen(
                            state = viewModel.state.value,
                            onClick = {
                                viewModel.onEvent(
                                    NotesEvent.Filter(
                                        NotesFilter.Tag(it)
                                    )
                                )
                            }
                        )
                    },
                    drawerGesturesEnabled = true,
                    scaffoldState = scaffoldState
//                    bottomBar = {
//                        BottomNav(navController = navController)
//                    },
                ) {
                    NavigationGraph(navController)
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

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Screen.NotesScreen.route
        ) {
            composable(route = Screen.NotesScreen.route) {
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
//                    navArgument("noteColor"){
//                        type = NavType.IntType
//                        defaultValue = -1
//                    }
                )
            ) {
                EditNoteUIScreen(
                    navController = navController
                )
            }
            composable(Screen.TagsScreen.route) {
                TagsScreen()
            }
            composable(Screen.SearchScreen.route) {
                SearchScreen()
            }
        }
    }

    @Composable
    fun TagsScreen(){

    }

    @Composable
    fun SearchScreen(){

    }

    @Composable
    fun BottomNav(navController: NavController) {
        val items = listOf(
            Screen.TagsScreen,
            Screen.NotesScreen,
            Screen.SearchScreen
        )
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                       },
                    label = { Text(text = item.title, fontSize = 12.sp) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {

                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}