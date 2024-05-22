package com.example.watch.main

import com.example.watch.Model.Movie

class MainContact {
    interface PresenterInterface {
        fun getMyMoviesList()
        fun stop()
        fun onDeleteTapped(selectedMovies: HashSet<*>)
    }
    interface ViewInterface {
        fun displayMovies (movieList: List <Movie>)
        fun displayNoMovies ()
        fun displayMessage (message: String )
        fun displayError (message: String )
    }
}