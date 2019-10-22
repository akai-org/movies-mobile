package pl.org.akai.movies.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_search_movie.*
import pl.org.akai.movies.R
import pl.org.akai.movies.data.SearchRespone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMovieFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_search_movie


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsButton.setOnClickListener {
            findNavController().navigate(R.id.toMovieDetails)
        }
        service.getSerchedMovies("6ade0e7b", "abc").enqueue(object : Callback<SearchRespone> {
            override fun onFailure(call: Call<SearchRespone>, t: Throwable) {}

            override fun onResponse(call: Call<SearchRespone>, response: Response<SearchRespone>) {
                when (response.code()) {
                    200 -> {
                        for (movie in response.body()!!.search) {
                            Log.d("MyLog", "$movie")
                        }
                    }
                    else -> {
                        Log.d("MyLog", "Call: ${call.request().url()}")
                        Log.d("MyLog", "${call.request().headers()}")
                    }
                }
            }

        })
    }
}
