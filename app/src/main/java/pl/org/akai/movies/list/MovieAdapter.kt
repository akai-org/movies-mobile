package pl.org.akai.movies.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_list_item.view.*
import pl.org.akai.movies.R
import pl.org.akai.movies.data.Movie

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var items: ArrayList<Movie> = ArrayList()
    private var itemsFull: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(movieList: ArrayList<Movie>) {
//        są 2 listy, full do wszytkich elementów, druga do searchview - będą z niej usuwane elementy podczas szukania
//        takie przypisanie powoduje że listy są od siebie niezależne
        items = movieList
        itemsFull = ArrayList(movieList)
    }

    class MovieViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.poster
        val movieTitle: TextView = itemView.title
        val movieYear: TextView = itemView.year
        val movieType: TextView = itemView.type

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieYear.text = movie.year
            movieType.text = movie.type

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(movie.poster)
                .into(moviePoster)

        }
    }

    override fun getFilter(): Filter {
        return movieFilter
    }

    private val movieFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Movie> = ArrayList()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(itemsFull)
            } else {
                val filterPattern: String = constraint.toString().toLowerCase().trim()

//                dodawanie do przefiltrowanej listy elementów tych z szukaną frazą w tytule, typie lub roku produkcji
                for (item in itemsFull) {
                    if (item.title.toLowerCase().contains(filterPattern) ||
                        item.year.toLowerCase().contains(filterPattern) ||
                        item.type.toLowerCase().contains(filterPattern)
                    ) {
                        filteredList.add(item)
                    }
                }
            }
//            zwracanie razultatów wyszukiwania na podstawie przefiltrowanej listy do publishResults
            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            items.clear()
            items.addAll(results?.values as ArrayList<Movie>)
            notifyDataSetChanged()
        }
    }

}