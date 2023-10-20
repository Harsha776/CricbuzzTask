package com.example.cricbuzztask.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricbuzztask.Model.Sneaker
import com.example.cricbuzztask.R

class SneakerAdapter: RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder>() {

    private val data:ArrayList<Sneaker> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):SneakerViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_popular, parent, false)

        return SneakerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        holder.tvSneakerName.setText(data.get(position).name)
        holder.tvPrice.setText("$"+ data.get(position).retailPrice)
        Glide.with(holder.tvSneakerName.context)
            .load(R.drawable.ic_launcher_foreground)
            .into(holder.ivSneakerImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data:ArrayList<Sneaker>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class SneakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvSneakerName: TextView =itemView.findViewById(R.id.tvSneakerName)
        val tvPrice: TextView =itemView.findViewById(R.id.tvPrice)
        val ivSneakerImage : ImageView = itemView.findViewById(R.id.ivSneakerImage)
    }
}