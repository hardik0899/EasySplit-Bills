package com.hardik.easysplitbills.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hardik.easysplitbills.R
import com.hardik.easysplitbills.database.friends.FriendEntity
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class FriendShareAdapter(val context: Context,
                         private val itemList: ArrayList<FriendEntity>,
                         private val indCost: Double,
                         private val flag : Boolean):
                            RecyclerView.Adapter<FriendShareAdapter.FriendShareHolder>(){

    private val boolArray = ArrayList(Collections.nCopies(itemCount,false))

    class FriendShareHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFriendName: TextView = view.findViewById(R.id.txtFriendName)
        val etShare: EditText = view.findViewById(R.id.etShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendShareHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_single_expense,parent,false)
        return FriendShareHolder(view)
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: FriendShareHolder, position: Int) {
        val itemObject = itemList[holder.adapterPosition]
        holder.txtFriendName.text = itemObject.friend_name
        if(flag) {
            if(!boolArray[holder.adapterPosition]) {
                holder.etShare.setText(BigDecimal(indCost).toPlainString())
                boolArray[holder.adapterPosition] = true
            }
        }
    }
}