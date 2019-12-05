package pl.org.akai.movies.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_singin.*
import pl.org.akai.movies.R


class SignInFragment : BaseFragment() {

    private lateinit var auth: FirebaseAuth

    override val layoutId: Int
        get() = R.layout.fragment_singin

    private fun updateUI() {
        findNavController().navigate(SignInFragmentDirections.toMainActivity())
        activity?.finish()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        signInButton.setOnClickListener {
            val v_login = login.text.toString()
            val v_password = password.text.toString()
            singIn(v_login, v_password)
        }

        signUpButton.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.toSingUp())
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
