package pl.org.akai.movies.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavArgsLazy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_search_movie.view.*
import kotlinx.android.synthetic.main.fragment_singin.*
import pl.org.akai.movies.R
import pl.org.akai.movies.activities.MainActivity


class SignInFragment : BaseFragment() {

    private lateinit var auth: FirebaseAuth

    override val layoutId: Int
        get() = R.layout.fragment_singin

    private fun updateUI() {
        val intent = Intent(context, MovieDetailsFragment::class.java)
        startActivity(intent)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        button.setOnClickListener {
            Log.d("mycall:", "clicked")

            val v_login = login.text.toString()
            val v_password = password.text.toString()

            Log.d("My2 log:", v_login)
            Log.d("My2 pass:", v_password)


            singIn(v_login, v_password)
        }
    }

    private fun singIn(login: String, pass: String) {
        auth.signInWithEmailAndPassword(login, pass)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "signInWithEmail:success")
                    //val user = auth.currentUser
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

}
