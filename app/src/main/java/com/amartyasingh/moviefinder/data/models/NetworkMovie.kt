package com.amartyasingh.moviefinder.data.models

import com.google.gson.annotations.SerializedName

data class NetworkMovie(
    @SerializedName("id")val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val rating: Double?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val language: String,
)
