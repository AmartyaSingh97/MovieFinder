package com.amartyasingh.moviefinder.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.amartyasingh.moviefinder.data.local.db.MovieDatabase
import com.amartyasingh.moviefinder.data.local.entity.FavoriteMovie
import com.amartyasingh.moviefinder.network.ApiService
import com.amartyasingh.moviefinder.utils.constants.apiKey
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class MoviePagingSource @Inject constructor(private val movieApi: ApiService,
                                            private val movieDatabase: MovieDatabase)
    : PagingSource<Int, FavoriteMovie>() {

    var language = "en"

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteMovie> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {

            val response = movieApi.getMovies(apiKey, page, language)

            movieDatabase.withTransaction {
                if (page == STARTING_PAGE_INDEX) {
                    movieDatabase.movieDao.clearAllMovies()
                }
                movieDatabase.movieDao.insertAll(response.results)
            }
            val movieDataFromDB = movieDatabase.movieDao.getMovieByLanguage(language)

            LoadResult.Page(
                data = movieDataFromDB,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FavoriteMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
