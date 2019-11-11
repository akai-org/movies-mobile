package pl.org.akai.movies.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavArgsLazy
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_search_movie.view.*
import kotlinx.android.synthetic.main.fragment_singin.*
import pl.org.akai.movies.R


class SignInFragment : BaseFragment() {


    override val layoutId: Int
        get() = R.layout.fragment_singin

    val args = NavArgsLazy(MovieDetailsFragmentArgs::class) {
        arguments!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var auth: FirebaseAuth

        button.setOnClickListener {
            Log.d("mycall:", "clicked")

            val v_login = login.text.toString()
            val v_password = password.text.toString()

            Log.d("My2 log:", v_login)
            Log.d("My2 pass:", v_password)


            auth.signInWithEmailAndPassword(v_login, v_password)

        }

    }

}
