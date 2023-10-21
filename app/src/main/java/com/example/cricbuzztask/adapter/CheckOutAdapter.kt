package com.example.cricbuzztask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricbuzztask.Model.Sneaker
import com.example.cricbuzztask.R
class CheckOutAdapter(private val onClick:(String)->Unit): RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder>() {

    private val data:ArrayList<Sneaker> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):CheckOutViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_checkout, parent, false)

        return CheckOutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckOutViewHolder, position: Int) {
        holder.tvSneakerName.setText(data.get(position).name)
        holder.tvPrice.setText("$ "+data.get(position).retailPrice)
        holder.ivCancel.setOnClickListener {
            onClick(data.get(position).id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data:ArrayList<Sneaker>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class CheckOutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvSneakerName: TextView =itemView.findViewById(R.id.tvSneakerName)
        val tvPrice: TextView =itemView.findViewById(R.id.tvPrice)
        val ivCancel : ImageView = itemView.findViewById(R.id.ivCancel)
    }
}