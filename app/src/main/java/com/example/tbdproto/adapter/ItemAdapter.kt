package com.example.tbdproto.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tbdproto.R
import com.example.tbdproto.RunningMap
import com.example.tbdproto.model.MainCardTitles
import android.os.Bundle
import com.example.tbdproto.PopularRuns
import com.example.tbdproto.RecentRuns

class ItemAdapter (private val context: Context,
                   private val dataset: List<MainCardTitles>
                   ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text =  context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)

        holder.imageView.setOnClickListener () {
            val context = holder.view.context
            if (holder.textView.text == "Start Your Run") {
                val intent = Intent(context, RunningMap::class.java).apply {
                    putExtra("map", holder.textView.text)
                }
                context.startActivity(intent)
            }

            if (holder.textView.text == "Last Run") {
                val intent = Intent(context, RecentRuns::class.java).apply {
                    putExtra("recent", holder.textView.text)
                }
                context.startActivity(intent)
            }

            if (holder.textView.text == "Random Run For You") {
                val intent = Intent(context, PopularRuns::class.java).apply {
                    putExtra("popular", holder.textView.text)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = dataset.size
}



