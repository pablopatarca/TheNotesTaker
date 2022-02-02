package app.pablopatarca.thenotestaker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import app.pablopatarca.thenotestaker.R
import app.pablopatarca.thenotestaker.domain.NotesEvent
import app.pablopatarca.thenotestaker.domain.NotesFilter
import app.pablopatarca.thenotestaker.ui.drawer.TagsComponentScreen
import app.pablopatarca.thenotestaker.ui.edit.EditNoteUIScreen
import app.pablopatarca.thenotestaker.ui.main.MainUIScreen
import app.pablopatarca.thenotestaker.ui.main.NotesViewModel
import app.pablopatarca.thenotestaker.ui.theme.TheNotesTakerTheme
import app.pablopatarca.thenotestaker.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NotesViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TheNotesTakerTheme {
                val scaffoldState = rememberScaffoldState(
                    drawerState = rememberDrawerState(DrawerValue.Closed)
                )
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                Scaffold(
                    topBar = {
                        TopBarUI(
                            scaffoldState = scaffoldState,
                            onSearchClick = {
                                navController.navigate(
                                    Screen.SearchScreen.route
                                )
                            }
                        )
                    },
                    drawerContent = {
                        Text(
                            text = stringResource(id = R.string.drawer_menu_title),
                            modifier = Modifier.padding(16.dp,32.dp,16.dp,32.dp),
                            fontSize = 30.sp
                        )
                        Divider()
                        TagsComponentScreen(
                            state = viewModel.state.value,
                            onClick = {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
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