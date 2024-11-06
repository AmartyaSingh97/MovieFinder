package com.amartyasingh.moviefinder.data.models

import com.amartyasingh.moviefinder.data.local.entity.FavoriteMovie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<FavoriteMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
