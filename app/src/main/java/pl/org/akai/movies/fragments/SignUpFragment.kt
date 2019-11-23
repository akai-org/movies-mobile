package pl.org.akai.movies.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import pl.org.akai.movies.R


class SignUpFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_sign_up

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        signUpButton.setOnClickListener {
            val email: String = emailEditText.text.toString()
            val password: String = (passwordEditText as TextInputEditText).text.toString()
            createUserWithEmailAndPassword(email, password)
        }

    }


    private fun createUserWithEmailAndPassword(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) { // rest of validation is handled by firebase
            showErrorMessage(getString(R.string.wrongEmailOrPassword))
            return
        }
        signUpProgressBar.isVisible = true
        signUpButton.isVisible = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "createUserWithEmail:success")
                    errorTextView.text = ""
                    Toast.makeText(context, getString(R.string.accountCreated), Toast.LENGTH_LONG)
                        .show()
                    auth.addAuthStateListener {
                        val user = it.currentUser
                        user?.sendEmailVerification()?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Firebase", "Email sent.")
                            }
                        }
                    }

                } else {
                    Log.d("Firebase", "createUserWithEmail: error: ${task.exception}")
                    showErrorMessage(task.exception!!.localizedMessage)
                }
                signUpProgressBar.isVisible = false
                signUpButton.isVisible = true
            }
    }

    private fun showErrorMessage(message: String) {
//        passwordEditText.text
        errorTextView.text = message
    }


}
