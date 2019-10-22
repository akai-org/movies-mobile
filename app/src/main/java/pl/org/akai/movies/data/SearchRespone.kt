package pl.org.akai.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchRespone(
    @Expose
    @SerializedName("Search")
    val search: List<Movie>,
    @Expose
    @SerializedName("totalResults")
    val totalResults: String,
    @Expose
    @SerializedName("Response")
    val response: String
)
