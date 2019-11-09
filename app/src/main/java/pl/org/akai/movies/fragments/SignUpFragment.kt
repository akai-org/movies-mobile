package pl.org.akai.movies.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import pl.org.akai.movies.R


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        signUpButton.setOnClickListener {
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            createUserWithEmailAndPassword(email, password)
        }
    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            showErrorMessage(getString(R.string.wrongEmailOrPassword))
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "createUserWithEmail:success")
                    errorTextView.text = ""
                    Toast.makeText(context, getString(R.string.accountCreated), Toast.LENGTH_LONG)
                        .show()

                } else {
                    Log.d("Firebase", "createUserWithEmail: error: ${task.exception}")
                    showErrorMessage(task.exception!!.localizedMessage!!)
                }
            }
    }

    private fun showErrorMessage(message: String) {
        passwordEditText.text.clear()
        errorTextView.text = message
    }


}
