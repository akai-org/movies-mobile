package pl.org.akai.movies.fragments


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_search_movie.*
import pl.org.akai.movies.R

class SearchMovieFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_search_movie


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsButton.setOnClickListener {
            findNavController().navigate(R.id.toMovieDetails)
        }
    }
}
