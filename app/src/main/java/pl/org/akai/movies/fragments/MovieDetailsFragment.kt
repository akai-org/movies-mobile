package pl.org.akai.movies.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
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

        title.text = detailsResponse.title
        year.text = getString(R.string.year, detailsResponse.year)
        rated.text = getString(R.string.rated, detailsResponse.rated)
        released.text = getString(R.string.released, detailsResponse.released)
        genre.text = getString(R.string.genre, detailsResponse.genre)
        director.text = getString(R.string.director, detailsResponse.director)
        runtime.text = getString(R.string.runtime, detailsResponse.runtime)
        writer.text = getString(R.string.writer, detailsResponse.writer)
        actors.text = getString(R.string.actors, detailsResponse.actors)
        metascore.text = getString(R.string.metascore, detailsResponse.metascore)
        dvd.text = getString(R.string.dvd, detailsResponse.dvd)
        boxOffice.text = getString(R.string.box_office, detailsResponse.boxOffice)
        production.text = getString(R.string.production, detailsResponse.production)
        website.text = getString(R.string.website, detailsResponse.website)

        plot.text = detailsResponse.plot

        Glide.with(context!!).load(detailsResponse.poster).into(poster)
    }
}
