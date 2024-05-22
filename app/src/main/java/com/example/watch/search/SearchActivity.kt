package com.example.watch.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watch.API.ClientAPI
import com.example.watch.Model.Item
import com.example.watch.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "SearchActivity"
class SearchActivity : AppCompatActivity() {
    private lateinit var list: List<Item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var query = ""
    lateinit var mytext: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.search_results_recyclerview)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mytext = findViewById(R.id.no_movies_textview)

        val i = intent
        query = i.getStringExtra(SEARCH_QUERY) ?: ""

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val clientApi: ClientAPI = retrofit.create<ClientAPI>(ClientAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = clientApi.fetchResponse("6266644e",query)
            runOnUiThread {
                list = response.items?: emptyList()
                adapter = SearchAdapter(list, itemListener, this@SearchActivity)
                recyclerView.adapter = adapter
                progressBar.visibility = View.INVISIBLE
            }
            Log.d(TAG, "Response received: $response")
        }
    }
    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(view: View, position: Int) {
            val movie = adapter.getItemAtPosition(position)
            Log.d(TAG, movie.title)
            Log.d(TAG, movie.getReleaseYearFromDate().toString())
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_TITLE, movie.title)
            replyIntent.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate().toString())
            replyIntent.putExtra(EXTRA_POSTER_PATH, movie.posterPath)
            setResult(RESULT_OK, replyIntent)

            finish()

        }
    }
    companion object {
        val SEARCH_QUERY = "searchQuery"
        val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }
    interface RecyclerItemListener {
        fun onItemClick(v: View, position: Int)
    }
}