package com.example.watch.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert
    fun insert(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getAll(): Observable<List<Movie>>

    @Delete
    fun delete(movie: Movie)
}