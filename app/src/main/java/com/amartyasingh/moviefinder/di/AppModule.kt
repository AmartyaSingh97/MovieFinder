package com.amartyasingh.moviefinder.di

import android.content.Context
import androidx.room.Room
import com.amartyasingh.moviefinder.data.local.db.MovieDatabase
import com.amartyasingh.moviefinder.data.remote.MoviePagingSource
import com.amartyasingh.moviefinder.network.ApiService
import com.amartyasingh.moviefinder.utils.constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase) = database.movieDao


    @Provides
    fun providePaginationSource(apiService: ApiService, database: MovieDatabase): MoviePagingSource {
        return MoviePagingSource(apiService, database)
    }

}