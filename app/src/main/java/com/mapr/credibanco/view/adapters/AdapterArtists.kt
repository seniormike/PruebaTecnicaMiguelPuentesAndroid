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

class AdapterArtists(
    private val context: Context,
    private var list: List<DataArtist>,
    private var listener: OnClickDetail
) :
    RecyclerView.Adapter<AdapterArtists.ViewHolder>() {

    fun setData(list: List<DataArtist>) {
        this.list = list
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var name: TextView = mView.findViewById(R.id.txt_name)
        var listeners: TextView = mView.findViewById(R.id.txt_listeners)
        var url: TextView = mView.findViewById(R.id.txt_url)
        var image: ImageView = mView.findViewById(R.id.image_artist)
        var detail: TextView = mView.findViewById(R.id.click_detail)
        var delete: TextView = mView.findViewById(R.id.click_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.name.text = item.name
        holder.listeners.text = item.listeners
        holder.url.text = item.url
        //holder.image = item.card
        holder.detail.setOnClickListener {
            listener.onClickDetail(item)
        }
        holder.delete.setOnClickListener {
            listener.onClickDelete(item)
        }
    }

    interface OnClickDetail {
        fun onClickDetail(item: DataArtist)
        fun onClickDelete(item: DataArtist)
    }
}