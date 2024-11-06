package com.amartyasingh.moviefinder.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class FavoriteMovie(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val overview: String?,
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,
    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    val language: String?,
    val isFavorite: Boolean = false
)
