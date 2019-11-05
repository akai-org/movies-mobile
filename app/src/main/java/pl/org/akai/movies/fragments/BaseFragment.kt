package pl.org.akai.movies.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import okhttp3.OkHttpClient
import pl.org.akai.movies.BuildConfig
import pl.org.akai.movies.services.FirebaseService
import pl.org.akai.movies.services.OMDbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    private lateinit var token: String

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(OMDbService::class.java)


    val client = OkHttpClient.Builder().addInterceptor { chain ->

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        chain.proceed(newRequest)
    }.build()

    private val firebaseRetrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.FIREBASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val firebaseService = firebaseRetrofit.create(FirebaseService::class.java)

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            layoutId, container, false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences =
            activity!!.getSharedPreferences("pl.org.akai.userPref", Activity.MODE_PRIVATE)
        token = preferences.getString(TOKEN, "")!!
    }

    companion object {
        const val TOKEN = "TOKEN"
    }
}