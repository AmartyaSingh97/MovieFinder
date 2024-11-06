package com.amartyasingh.moviefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.amartyasingh.moviefinder.presentation.LanguageFilterBottomSheet
import com.amartyasingh.moviefinder.presentation.MovieDetailsScreen
import com.amartyasingh.moviefinder.presentation.MoviesListScreen
import com.amartyasingh.moviefinder.ui.theme.MovieFinderTheme
import com.amartyasingh.moviefinder.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    var showLanguageFilterSheet by rememberSaveable { mutableStateOf(false) }

                    val vm = hiltViewModel<MainViewModel>()
                    val movieList = vm.movieResponse.collectAsLazyPagingItems()


                    NavHost(navController = navController, startDestination = "movies_list") {

                        //List Screen

                        composable("movies_list") {
                            Scaffold(modifier = Modifier.fillMaxSize(),
                                topBar = {
                                    TopAppBar(
                                        title = { Text(text = "Movie Stalker") },
                                        actions = {
                                            IconButton(
                                                onClick = {
                                                    showLanguageFilterSheet = true
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.FilterAlt,
                                                    contentDescription = "Language Filter"
                                                )
                                            }
                                        }
                                    )
                                }
                            ) { innerPadding ->
                                LanguageFilterBottomSheet(
                                    selectedLang = vm.choosenLanguage,
                                    isShowingState = showLanguageFilterSheet,
                                    onDismissRequest = { showLanguageFilterSheet = false },
                                    onApplyClick = { vm.setLanguage(it) }
                                )

                                MoviesListScreen(
                                    movies = movieList,
                                    modifier = Modifier.padding(innerPadding),
                                    onMovieClicked = { navController.navigate("movie_details/$it") },
                                    viewModel = vm
                                )
                            }
                        }

                        //Details Screen

                        composable("movie_details/{movieId}",
                            arguments = listOf(navArgument("movieId") { type = NavType.IntType }))
                        { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
                            MovieDetailsScreen(movieId = movieId, viewModel = vm,
                                onFavCLicked = { vm.toggleFavorite(it) })
                        }
                    }
                }
            }
        }
    }
}

