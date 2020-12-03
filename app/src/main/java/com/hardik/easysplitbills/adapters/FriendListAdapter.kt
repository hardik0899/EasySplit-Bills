package com.hardik.easysplitbills.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hardik.easysplitbills.R
import com.hardik.easysplitbills.database.friends.FriendEntity
import java.util.Collections
import kotlin.collections.ArrayList

class FriendListAdapter(val context: Context, private val itemList: ArrayList<FriendEntity>):
    RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {
    var checkList: ArrayList<FriendEntity> = arrayListOf()
    var boolArray = ArrayList(Collections.nCopies(itemCount,false))

    init {
        checkList = arrayListOf()
        boolArray = ArrayList(Collections.nCopies(itemCount,false))
    }
    class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFriendName: TextView = view.findViewById(R.id.txtFriendName)
        val imgCheck: ImageView = view.findViewById(R.id.imgCheck)
        val recyclerLayout: CardView = view.findViewById(R.id.recyclerLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_single_friend,parent,false)
        return FriendViewHolder(view)
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val itemObject = itemList[holder.adapterPosition]
        holder.txtFriendName.text = itemObject.friend_name
        holder.imgCheck.setImageResource(if(boolArray[holder.adapterPosition]) R.drawable.ic_cb_full else R.drawable.ic_cb_empty)
        holder.recyclerLayout.setOnClickListener {
            if(checkList.contains(itemObject)) {
                holder.imgCheck.setImageResource(R.drawable.ic_cb_empty)
                checkList.remove(itemObject)
                boolArray[holder.adapterPosition] = false
            } else {
                holder.imgCheck.setImageResource(R.drawable.ic_cb_full)
                checkList.add(itemObject)
                boolArray[holder.adapterPosition] = true
            }
        }
    }
}