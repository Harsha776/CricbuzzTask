package com.example.cricbuzztask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricbuzztask.Model.Sneaker
import com.example.cricbuzztask.R

/**
 * Checkout Adapter class is used to show all the selected cart items
 */
class CheckOutAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder>() {

    private val data: ArrayList<Sneaker> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckOutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_checkout, parent, false)

        return CheckOutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckOutViewHolder, position: Int) {
        holder.tvSneakerName.text = data[position].name
        holder.tvPrice.text = "$ " + data[position].retailPrice
        holder.ivCancel.setOnClickListener {
            onClick(data[position].id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: ArrayList<Sneaker>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class CheckOutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSneakerName: TextView = itemView.findViewById(R.id.tvSneakerName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val ivCancel: ImageView = itemView.findViewById(R.id.ivCancel)
    }
}