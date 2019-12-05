package pl.org.akai.movies.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import pl.org.akai.movies.BuildConfig
import pl.org.akai.movies.R
import pl.org.akai.movies.data.DetailsResponse
import pl.org.akai.movies.services.OMDbService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(OMDbService::class.java)

    private val args: DetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)

        title = ""
        toolbar.title = ""
        toolbar.navigationIcon = getDrawable(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        service.getMovieDetails("dbeb1564", args.imdbId)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupMovieData(detailsResponse: DetailsResponse) {
        Log.d("MyLog", "$detailsResponse")
        titleTv.text = detailsResponse.title
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

        Glide.with(this).load(detailsResponse.poster).into(poster)
    }


    private fun checkNull(text: String?, textView: TextView, sId: Int) {
        if (text == null) {
            textView.isVisible = false
        } else {
            textView.text = getString(sId, text)
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}