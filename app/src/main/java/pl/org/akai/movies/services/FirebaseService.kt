package pl.org.akai.movies.services

import pl.org.akai.movies.data.FavoritesResponse
import retrofit2.Call
import retrofit2.http.GET

interface FirebaseService {

    @GET("/getFavoritesMovies")
    fun getFavoriteMovies():
            Call<FavoritesResponse>
}