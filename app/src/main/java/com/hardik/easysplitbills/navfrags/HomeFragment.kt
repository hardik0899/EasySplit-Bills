package com.hardik.easysplitbills.navfrags

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hardik.easysplitbills.ExpenseActivity
import com.hardik.easysplitbills.MainActivity
import com.hardik.easysplitbills.database.friends.DBAsyncTaskFriend
import com.hardik.easysplitbills.database.friends.FriendEntity
import com.hardik.easysplitbills.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnAddFrnd.setOnClickListener {
            addFriend()
        }
        binding.btnAddExp.setOnClickListener {
            startActivity(Intent(activity, ExpenseActivity::class.java))
        }
        return binding.root
    }

    private fun addFriend() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        val myLayout = LinearLayout(context)
        myLayout.orientation = LinearLayout.VERTICAL

        alertDialog.setTitle("Add Friend")
        alertDialog.setMessage("Add Unique Names Only")
        alertDialog.setCancelable(false)

        val input1 = EditText(context)
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20F,context?.resources?.displayMetrics).toInt()
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(px,0,px,0)
        input1.layoutParams = lp
        myLayout.addView(input1)
        alertDialog.setView(myLayout)
        alertDialog.setPositiveButton("Cancel") { _, _ -> }
        alertDialog.setNegativeButton("Add") { _, _ ->
            try {
                val name = input1.text.toString()
                if(name.isEmpty()) {
                    Toast.makeText(context, "Input a valid friend name",Toast.LENGTH_LONG).show()
                } else {
                    var getter = DBAsyncTaskFriend(
                        (activity as MainActivity).applicationContext,
                        FriendEntity(name, 0.00),
                        1
                    ).execute().get()
                    if(getter) {
                        Toast.makeText(context, "Friend already exist, use another name",Toast.LENGTH_LONG).show()
                    } else {
                        getter = DBAsyncTaskFriend(
                            (activity as MainActivity).applicationContext,
                            FriendEntity(
                                name,
                                0.00
                            ),
                            2
                        ).execute().get()
                        if(getter) {
                            Toast.makeText(context, "Added Friend : $name",Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Friend not added! Try again",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: RuntimeException) {
                Toast.makeText(context,"Can't Add Friend, Try Again",Toast.LENGTH_LONG).show()
            }
        }
        alertDialog.show()
    }
}
