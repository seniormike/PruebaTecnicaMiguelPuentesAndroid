package com.mapr.credibanco.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapr.credibanco.model.db.DataArtist
import com.mapr.credibanco.R
import com.mapr.credibanco.model.tracks.TrackOBJ
import com.squareup.picasso.Picasso

class AdapterSongs(
    private val context: Context,
    private var list: List<TrackOBJ>,
) :
    RecyclerView.Adapter<AdapterSongs.ViewHolder>() {

    fun setData(list: List<TrackOBJ>) {
        this.list = list
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var name: TextView = mView.findViewById(R.id.txt_name_song)
        var listeners: TextView = mView.findViewById(R.id.txt_listeners)
        var playcount: TextView = mView.findViewById(R.id.txt_playcount)
        var url: TextView = mView.findViewById(R.id.txt_url)
        var image: ImageView = mView.findViewById(R.id.image_song)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.name.text = item.name
        holder.listeners.text = item.listeners
        holder.playcount.text = item.playcount
        holder.url.text = item.url
        if (item.image.isNotEmpty()) {
            Picasso.get().load(item.image[0].text).into(holder.image)
        }
    }
}