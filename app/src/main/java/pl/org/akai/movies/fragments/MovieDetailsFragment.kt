package pl.org.akai.movies.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_movie_details.*
import pl.org.akai.movies.R
import pl.org.akai.movies.data.DetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_movie_details


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.toSearchMovieFragment)
        }
        service.getMovieDetails("dbeb1564", "tt3896198")
            .enqueue(object : Callback<DetailsResponse> {
                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {}

                override fun onResponse(
                    call: Call<DetailsResponse>, response: Response<DetailsResponse>
                ) {
                    when (response.code()) {
                        200 -> Log.d("MyLogMDF", response.body()!!.toString())
                    }
                }

            })
    }
}

