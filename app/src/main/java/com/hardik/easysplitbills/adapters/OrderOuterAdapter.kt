package com.hardik.easysplitbills.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hardik.easysplitbills.R
import com.hardik.easysplitbills.database.expenses.ExpenseEntity

class OrderOuterAdapter(val context: Context,
                        val expenses: ArrayList<ExpenseEntity>,
                        private val arr1: ArrayList<ArrayList<String>>,
                        private val arr2: ArrayList<ArrayList<String>>):
                            RecyclerView.Adapter<OrderOuterAdapter.OuterViewHolder>() {

    class OuterViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val txtDate:TextView = view.findViewById(R.id.txtDate)
        val txtDesc:TextView = view.findViewById(R.id.txtDesc)
        val txtTotal:TextView = view.findViewById(R.id.txtTotal)
        val recyclerInner:RecyclerView = view.findViewById(R.id.recyclerInner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_order_outer,parent,false)
        return OuterViewHolder(view)
    }

    override fun getItemCount() = expenses.size

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        holder.txtDate.text = expenses[holder.adapterPosition].expense_id
        holder.txtTotal.text = "Rs. "+expenses[holder.adapterPosition].expenseCost.toString()
        holder.txtDesc.text = expenses[holder.adapterPosition].epenseDesc
        val linearLayoutManager = LinearLayoutManager(context)
        val recyclerAdapter = OrderInnerAdapter(context,arr1[holder.adapterPosition],arr2[holder.adapterPosition])
        holder.recyclerInner.layoutManager = linearLayoutManager
        holder.recyclerInner.adapter = recyclerAdapter
    }
}