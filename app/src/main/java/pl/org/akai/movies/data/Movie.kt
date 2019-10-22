package pl.org.akai.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @Expose
    @SerializedName("Title")
    val title: String,
    @Expose
    @SerializedName("Year")
    val year: String,
    @Expose
    @SerializedName("imdbID")
    val imdbId: String,
    @Expose
    @SerializedName("Type")
    val type: String,
    @Expose
    @SerializedName("Poster")
    val poster: String
)