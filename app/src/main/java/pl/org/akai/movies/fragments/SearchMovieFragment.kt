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

    private val movieAdapter = MovieAdapter(arrayListOf(), this)


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
                getMovies(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getMovies(newText)
                return false
            }
        })


        initRecyclerView()

    }

    private fun getMovies(query: String) {
        if (query.length > 3) { // poniżej 3 jest za dużo wyników
            service.getSerchedMovies("6ade0e7b", query).enqueue(object : Callback<SearchRespone> {
                override fun onFailure(call: Call<SearchRespone>, t: Throwable) {}

                override fun onResponse(
                    call: Call<SearchRespone>,
                    response: Response<SearchRespone>
                ) {
                    when (response.code()) {
                        200 -> {
                            movieAdapter.submitList(response.body()!!.search)
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
