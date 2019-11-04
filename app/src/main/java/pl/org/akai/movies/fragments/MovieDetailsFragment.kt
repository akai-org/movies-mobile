package pl.org.akai.movies.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
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
        backButton.setOnClickListener {
            findNavController().navigate(R.id.toSearchMovieFragment)
        }
        service.getMovieDetails("dbeb1564", "tt3896198")
            .enqueue(object : Callback<DetailsResponse> {
                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {}

                val appContext = context!!.applicationContext

                override fun onResponse(
                    call: Call<DetailsResponse>, response: Response<DetailsResponse>
                ) {
                    when (response.code()) {
                        200 ->
                        {
                            title.text = response.body()!!.title
                            year.text = "${year.text}${response.body()!!.year}"
                            rated.text = "${rated.text}${response.body()!!.rated}"
                            released.text = "${released.text}${response.body()!!.released}"
                            genre.text = "${genre.text}${response.body()!!.genre}"
                            director.text = "${director.text}${response.body()!!.director}"
                            runtime.text = "${runtime.text}${response.body()!!.runtime}"
                            writer.text = "${writer.text}${response.body()!!.writer}"
                            actors.text = "${actors.text}${response.body()!!.actors}"
                            metascore.text = "${metascore.text}${response.body()!!.metascore}"
                            dvd.text = "${dvd.text}${response.body()!!.dvd}"
                            boxOffice.text = "${boxOffice.text}${response.body()!!.boxOffice}"
                            production.text = "${production.text}${response.body()!!.production}"
                            website.text = "${website.text}${response.body()!!.website}"

                            plot.text = response.body()!!.plot

                            Glide.with(appContext).load(response.body()!!.poster).into(poster)


                        }


                    }





                }

            })
    }
}
