package com.amartyasingh.moviefinder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amartyasingh.moviefinder.data.local.dao.MovieDao
import com.amartyasingh.moviefinder.data.local.entity.FavoriteMovie
import com.amartyasingh.moviefinder.data.remote.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pagingSource: MoviePagingSource,
        private val movieDao: MovieDao
    ) : ViewModel() {

    private val _movieResponse: MutableStateFlow<PagingData<FavoriteMovie>> =
        MutableStateFlow(PagingData.empty())
    var movieResponse = _movieResponse.asStateFlow()
        private set

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.toggleFavoriteStatus(movieId)
            _movieData.value = movieDao.getMovieById(movieId)
        }
    }

    private val _language: MutableStateFlow<String> = MutableStateFlow("en")
    var choosenLanguage = _language.asStateFlow().value
        private set

    fun setLanguage(language: String) {
        _language.value = language
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.clearAllMovies()
        }
        getMovies()
    }

    private val _movieData = MutableStateFlow<FavoriteMovie?>(null)
    var movieData = _movieData.asStateFlow()
        private set

    fun getMovieById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieData.value = movieDao.getMovieById(movieId)
        }
    }

    private fun getMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            val currentLang =  _language.value
            Pager(
                config = PagingConfig(
                    50, enablePlaceholders = true
                )
            ) {
                pagingSource.apply {
                    language = currentLang
                }
            }.flow.cachedIn(viewModelScope).collect {
                _movieResponse.value = it
            }
        }
    }

    init {
        getMovies()
    }
}