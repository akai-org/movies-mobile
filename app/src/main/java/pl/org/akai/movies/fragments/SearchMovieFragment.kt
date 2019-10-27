package pl.org.akai.movies.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_movie.*
import pl.org.akai.movies.R
import pl.org.akai.movies.data.SearchRespone
import pl.org.akai.movies.list.MovieAdapter
import pl.org.akai.movies.list.TopSpacingItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMovieFragment : BaseFragment() {

    private lateinit var movieAdapter: MovieAdapter

    override val layoutId: Int
        get() = R.layout.fragment_search_movie


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsButton.setOnClickListener {
            findNavController().navigate(R.id.toMovieDetails)
        }

//        wyświetlanie przycisku done zamiast lupy na klawiaturze
        moviesSearchView.imeOptions = EditorInfo.IME_ACTION_DONE

        moviesSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                movieAdapter.filter.filter(newText)
                return false
            }
        })

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

        initRecyclerView()

    }

    private fun addDataSet() {
        TODO("Tutaj zamiast data należy wkleić listę danych do filmów")
        movieAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            movieAdapter = MovieAdapter()
            adapter = movieAdapter
        }


    }

}
