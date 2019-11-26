package pl.org.akai.movies.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_details.*
import pl.org.akai.movies.R
import pl.org.akai.movies.data.DetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_movie_details

    val args = NavArgsLazy(MovieDetailsFragmentArgs::class) {
        arguments!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imdbId = args.value.imdbId

        toolbar.navigationIcon = context!!.getDrawable(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            navigateBack()
        }

        service.getMovieDetails("dbeb1564", imdbId)
            .enqueue(object : Callback<DetailsResponse> {
                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {}

                override fun onResponse(
                    call: Call<DetailsResponse>, response: Response<DetailsResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.d("MyLogMDF", "${response.body()}")
                            setupMovieData(response.body()!!)
                        }
                    }
                }

            })
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.toSearchMovieFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.back, menu)
        val backItem: MenuItem = menu.findItem(R.id.action_back)
        val backButton: ImageButton = backItem.actionView as ImageButton
        backButton.setOnClickListener { findNavController().navigate(R.id.searchMovieFragment) }

        super.onCreateOptionsMenu(menu, inflater)
    }

    fun setupMovieData(detailsResponse: DetailsResponse) {
        Log.d("MyLog", "$detailsResponse")
        title.text = detailsResponse.title
        year.text = getString(R.string.year, detailsResponse.year)
        rated.text = getString(R.string.rated, detailsResponse.rated)

        checkNull(detailsResponse.released, released, R.string.released)
        checkNull(detailsResponse.genre, genre, R.string.genre)
        checkNull(detailsResponse.director, director, R.string.director)
        checkNull(detailsResponse.runtime, director, R.string.runtime)
        checkNull(detailsResponse.writer, writer, R.string.writer)
        checkNull(detailsResponse.actors, actors, R.string.actors)
        checkNull(detailsResponse.metascore, metascore, R.string.metascore)
        checkNull(detailsResponse.dvd, dvd, R.string.dvd)
        checkNull(detailsResponse.boxOffice, boxOffice, R.string.box_office)
        checkNull(detailsResponse.production, production, R.string.production)
        checkNull(detailsResponse.website, website, R.string.website)

        plot.text = detailsResponse.plot

        Glide.with(context!!).load(detailsResponse.poster).into(poster)
    }

    private fun checkNull(text: String?, textView: TextView, sId: Int) {
        if (text == null) {
            textView.isVisible = false
        } else {
            textView.text = getString(sId, text)
        }
    }
}
