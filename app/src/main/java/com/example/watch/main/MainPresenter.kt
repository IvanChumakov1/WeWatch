package com.example.watch.main

import android.util.Log
import com.example.watch.Model.LocalDataSource
import com.example.watch.Model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import android.support.annotation.NonNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private var viewInterface: MainContact.ViewInterface,
    private var dataSource: LocalDataSource
): MainContact.PresenterInterface {
    private val TAG = "MainPresenter"

    private val compositeDisposable = CompositeDisposable()
    val myMoviesObservable: io.reactivex.Observable<List<Movie>>
        get() = dataSource.allMovies
    val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {
            override fun onNext(movieList: List<Movie>) {
                if (movieList == null || movieList.size == 0) {
                    viewInterface.displayNoMovies()
                } else {
                    viewInterface.displayMovies(movieList)
                }
            }
            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error fetching movie list.", e)
                viewInterface.displayError("Error fetching movie list.")
            }
            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }


    override fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(myMoviesDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun onDeleteTapped(selectedMovies: HashSet<*>) {
        for (movie in selectedMovies) {
            dataSource.delete(movie as Movie)
        }
        if (selectedMovies.size == 1) {
            viewInterface.displayMessage("Movie deleted")
        } else if (selectedMovies.size > 1) {
            viewInterface.displayMessage("Movies deleted")
        }
    }
}