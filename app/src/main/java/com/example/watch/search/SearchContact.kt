package com.example.watch.search

import com.example.watch.Model.SearchResponse

class SearchContact {
    interface PresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
    }
    interface ViewInterface {
        fun displayResult(searchResponse: SearchResponse)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }

}