package pl.org.akai.movies.fragments


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_movie.*
import pl.org.akai.movies.R
import pl.org.akai.movies.data.FavoritesResponse
import pl.org.akai.movies.list.MovieAdapter
import pl.org.akai.movies.list.TopSpacingItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteMoviesFragment : BaseFragment() {

    private lateinit var movieAdapter: MovieAdapter

    override val layoutId: Int
        get() = R.layout.fragment_favorite_movies

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(arrayListOf()) {
            val action = FavoriteMoviesFragmentDirections.toMovieDetails(it.imdbId)
            findNavController().navigate(action)
        }

        getMovies("abc")
        setupToolbar()
        initRecyclerView()


    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.search_menu)
        val searchItem: MenuItem = toolbar.menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                getMovies(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getMovies(newText)
                return false
            }
        })
        searchView.queryHint = getText(R.string.search_view_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun getMovies(query: String) {
        if (query.length >= 3) { // poniżej 3 jest za dużo wyników
            firebaseService.getFavoriteMovies()
                .enqueue(object : Callback<FavoritesResponse> {
                    override fun onFailure(call: Call<FavoritesResponse>, t: Throwable) {}

                    override fun onResponse(
                        call: Call<FavoritesResponse>,
                        response: Response<FavoritesResponse>
                    ) {
                        when (response.code()) {
                            200 -> {
                                val favoritesResponse = response.body()!!
                                movieAdapter.submitList(favoritesResponse.favoriteMovies)
                                infoTextView.isVisible = favoritesResponse.favoriteMovies.isEmpty()
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

    private fun initRecyclerView() {
        moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            adapter = movieAdapter
        }


    }
}
