package pl.org.akai.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rating(
    @Expose
    @SerializedName("Source")
    val source: String,

    @Expose
    @SerializedName("Value")
    val value: String
)