package pl.org.akai.movies.fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_singin.*
import pl.org.akai.movies.R


class SignInFragment : BaseFragment() {

    private lateinit var auth: FirebaseAuth

    override val layoutId: Int
        get() = R.layout.fragment_singin

    private fun updateEnabled() {
        signInButton.isEnabled =
            emailEditText.text.toString().isNotBlank() && passwordEditText.text.toString().isNotBlank()
    }

    private fun openMainActivity() {
        findNavController().navigate(SignInFragmentDirections.toMainActivity())
        activity?.finish()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        signInButton.setOnClickListener {
            val login = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            singIn(login, password)
        }

        signUpButton.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.toSingUp())
        }

        emailEditText.addTextChangedListener {
            updateEnabled()
        }
        passwordEditText.addTextChangedListener {
            updateEnabled()
        }
    }

    private fun singIn(login: String, pass: String) {
        auth.signInWithEmailAndPassword(login, pass)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    auth.getAccessToken(true).addOnSuccessListener {
                        Log.d("MyLog", "Token: ${it.token}")
                        saveToken(it.token!!)
                        openMainActivity()
                    }
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

    private fun saveToken(token: String) {
        val preferences =
            activity!!.getSharedPreferences("pl.org.akai.userPref", Activity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

}
