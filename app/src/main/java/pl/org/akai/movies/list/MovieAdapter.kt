package pl.org.akai.movies.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_list_item.view.*
import pl.org.akai.movies.Constants
import pl.org.akai.movies.R
import pl.org.akai.movies.data.Movie

class MovieAdapter(val movies: ArrayList<Movie>, private val navController: NavController) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(movies[position])
            }
        }
    }

    fun submitList(movieList: List<Movie>) {
        movies.clear()
        movies.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val moviePoster: ImageView = itemView.poster
        private val movieTitle: TextView = itemView.title
        private val movieYear: TextView = itemView.year
        private val movieType: TextView = itemView.type

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieYear.text = movie.year
            movieType.text = movie.type

            itemView.setOnClickListener {
                val bundle = Bundle().apply { putString(Constants.IMBD_ID, movie.imdbId) }
                navController.navigate(R.id.toMovieDetails, bundle)
            }

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(movie.poster)
                .into(moviePoster)

        }
    }


}