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

        val imdbId = getImdbId(arguments)

        toolbar.navigationIcon = context!!.getDrawable(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            navigateBack()
        }
        backButton.setOnClickListener {
            navigateBack()
        }

        service.getMovieDetails("dbeb1564", imdbId)
            .enqueue(object : Callback<DetailsResponse> {
                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {}

                override fun onResponse(
                    call: Call<DetailsResponse>, response: Response<DetailsResponse>
                ) {
                    when (response.code()) {
                        200 -> Log.d("MyLogMDF", "${response.body()}")
                    }
                }
            })
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.toSearchMovieFragment)
    }

    fun getImdbId(bundle: Bundle?): String {
        val imdbId = bundle?.getString(IMDB_ID)
        requireNotNull(imdbId, {
            "Fragment requires Id from bundle to initialize"
        })
        return imdbId
    }

    companion object {
        private val IMDB_ID = "imdbId"

        fun createBundle(imbdId: String): Bundle {
            return Bundle().apply { putString(IMDB_ID, imbdId) }
        }
    }
}

