package com.example.watch.Model

import android.app.Application
import io.reactivex.Observable
import kotlin.concurrent.thread

open class LocalDataSource(application: Application) {

    private val movieDao: DAO
    open val allMovies: Observable<List<Movie>>

    init {
        val db = MovieDB.getDb(application)
        movieDao = db.getDao()
        allMovies = movieDao.getAll()
    }


    fun insert(movie: Movie) {
        thread {
            movieDao.insert(movie)
        }
    }

    fun delete(movie: Movie) {
        thread {
            movieDao.delete(movie)
        }
    }
}