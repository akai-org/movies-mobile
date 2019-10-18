package pl.org.akai.movies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.org.akai.movies.BuildConfig
import pl.org.akai.movies.services.OMDbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(OMDbService::class.java)

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            layoutId, container, false
        )
    }
}