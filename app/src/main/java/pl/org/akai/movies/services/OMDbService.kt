package pl.org.akai.movies.services

import pl.org.akai.movies.data.DetailsResponse
import pl.org.akai.movies.data.SearchRespone
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbService {

    @GET("/")
    fun getSerchedMovies(@Query("apikey") apikey: String, @Query("s") query: String):
            Call<SearchRespone>

    @GET("/")
    fun getMovieDetails(@Query("apikey") apikey: String, @Query("i") query: String):
            Call<DetailsResponse>
}