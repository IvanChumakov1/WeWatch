package com.example.watch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watch.Model.LocalDataSource
import com.example.watch.Model.Movie
import com.example.watch.Model.MovieDB
import com.example.watch.R
import com.example.watch.add.AddActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MainContact.ViewInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var imageEmpty:LinearLayout
    private lateinit var mainPresenter: MainContact.PresenterInterface
    private val TAG = "MainActivity"

    lateinit var dataBase: MovieDB

    private fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        mainPresenter = MainPresenter(this, dataSource)
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.movies_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        imageEmpty = findViewById(R.id.no_movies_layout)
        supportActionBar?.title = "Movies to Watch"
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.getMyMoviesList()
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.stop()
    }

    fun goToAddMovieActivity(v: View) {
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivityForResult(intent, ADD_VIEW_ACTIVITY_REQUEST_CODE)
    }

    fun deleteMovies(v: View) {
        mainPresenter.onDeleteTapped(adapter!!.selectedMovies)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_VIEW_ACTIVITY_REQUEST_CODE) {
            displayMessage("Фильм успешно добавлен")
            //loadView()
        } else {
            displayMessage("Нет добавленных фильмов")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setupViews()
    }

    override fun displayMovies(movieList: List<Movie>) {
        adapter = MainAdapter(movieList, this@MainActivity)
        recyclerView.adapter = adapter

        recyclerView.visibility = View.VISIBLE
        imageEmpty.visibility = View.INVISIBLE
    }

    override fun displayNoMovies() {
        Log.d(TAG, "No movies to display.")
        recyclerView.visibility = View.INVISIBLE
        imageEmpty.visibility = View.VISIBLE
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast. LENGTH_LONG )
            .show()
    }
    override fun displayError(message: String) {
        displayMessage (message)
    }

    companion object {
        const val ADD_VIEW_ACTIVITY_REQUEST_CODE = 1
    }
}