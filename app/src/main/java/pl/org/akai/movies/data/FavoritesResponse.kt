package pl.org.akai.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FavoritesResponse(
    @Expose
    @SerializedName("favoriteMovies")
    val favoriteMovies: List<Movie>
)