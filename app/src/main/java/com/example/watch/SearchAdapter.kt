package com.example.watch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watch.Model.Item
import com.squareup.picasso.Picasso

class SearchAdapter(var list: List<Item>, var listener: SearchActivity.RecyclerItemListener, var context: Context): RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_detal, parent, false)
        val viewHolder = SearchHolder(view)
        view.setOnClickListener { v -> listener.onItemClick(v, viewHolder.adapterPosition) }

        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.titleTextView.text = list[position].title
        holder.releaseDateTextView.text = list[position].releaseDate
        holder.overviewTextView.text = list[position].overview

        if (list[position].posterPath != "N/A") {
            Picasso.get().load(list[position].posterPath).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItemAtPosition(pos: Int): Item {
        return list[pos]
    }

    inner class SearchHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.title_textview)
        var overviewTextView: TextView = view.findViewById(R.id.overview_overview)
        var releaseDateTextView: TextView = view.findViewById(R.id.release_date_textview)
        var imageView: ImageView = view.findViewById(R.id.movie_imageview)

        init {
            view.setOnClickListener { v: View ->
                listener.onItemClick(v, adapterPosition)
            }
        }
    }

}