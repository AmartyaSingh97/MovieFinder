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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
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
                    Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Movie Stalker") },
                                actions = {
                                    IconButton(
                                        onClick = {  }
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
                        val vm = hiltViewModel<MainViewModel>()
                        val movieList = vm.movieResponse.collectAsLazyPagingItems()
                        MoviesListScreen(
                            modifier = Modifier.padding(innerPadding),
                            movies = movieList,
                            onFavClicked = { vm.toggleFavorite(it) }
                        )
                    }
                }
            }
        }
    }
}

