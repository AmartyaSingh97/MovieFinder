package com.amartyasingh.moviefinder.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amartyasingh.moviefinder.data.local.entity.FavoriteMovie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<FavoriteMovie>)

    @Query("Delete From movie")
    suspend fun clearAllMovies()

    @Query("Select * From movie")
    suspend fun getAllMovies(): List<FavoriteMovie>

    @Query("Select * From movie Where id = :id")
    suspend fun getMovieById(id: Int): FavoriteMovie

    @Query("Select * From movie Where original_language  = :language")
    suspend fun getMovieByLanguage(language: String): List<FavoriteMovie>

    @Query("UPDATE movie SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun toggleFavoriteStatus(movieId: Int)

    @Query("Select * From movie")
    fun pagingSource(): PagingSource<Int, FavoriteMovie>
}