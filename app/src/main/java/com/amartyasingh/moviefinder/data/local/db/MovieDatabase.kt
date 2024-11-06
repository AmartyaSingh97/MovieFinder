package com.amartyasingh.moviefinder.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amartyasingh.moviefinder.data.local.dao.MovieDao
import com.amartyasingh.moviefinder.data.local.entity.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao
}