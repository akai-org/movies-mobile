package pl.org.akai.movies.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import pl.org.akai.movies.MainActivityGraphDirections
import pl.org.akai.movies.R

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MyLog", "Selected")
        when (item.itemId) {
            R.id.nav_all -> {
                mainNavHostFragment.findNavController()
                    .navigate(MainActivityGraphDirections.globalToSearchMovieFragment())
            }
            R.id.nav_favorites -> {
                mainNavHostFragment.findNavController()
                    .navigate(MainActivityGraphDirections.globalToFavoritesMovies())
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
