package com.hardik.easysplitbills.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hardik.easysplitbills.R

class OrderInnerAdapter(val context: Context, private val arr1: ArrayList<String>, private val arr2: ArrayList<String>):
    RecyclerView.Adapter<OrderInnerAdapter.InnerViewHolder>() {

    class InnerViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val txtAttachName:TextView = view.findViewById(R.id.txtAttachName)
        val txtAttachShare: TextView = view.findViewById(R.id.txtAttachShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_order_inner,parent,false)
        return InnerViewHolder(view)
    }

    override fun getItemCount() = arr1.size

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.txtAttachName.text = arr1[holder.adapterPosition]
        holder.txtAttachShare.text = "Rs. "+arr2[holder.adapterPosition]
    }
}