package com.hardik.easysplitbills

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardik.easysplitbills.adapters.FriendListAdapter
import com.hardik.easysplitbills.database.friends.DBAsyncTaskFriend
import com.hardik.easysplitbills.database.friends.FriendEntity
import com.hardik.easysplitbills.database.friends.GetFriends
import com.hardik.easysplitbills.databinding.ActivityExpenseBinding
import java.util.*
import kotlin.Comparator

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: FriendListAdapter

    private var nameIncrease = Comparator<FriendEntity>{friend1,friend2 ->
        friend1.friend_name.compareTo(friend2.friend_name,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ExpenseActivity,R.layout.activity_expense)
        val isMoney = intent.getBooleanExtra("isMoney",false)
        if(isMoney) {
            binding.etCost.setText(intent.getStringExtra("Money"))
            binding.etDesc.setText(intent.getStringExtra("descBill"))
        }
        layoutManager = LinearLayoutManager(this@ExpenseActivity)
        supportActionBar?.title = "Add Expense"
        val friendsList = GetFriends(applicationContext).execute().get()
        Collections.sort(friendsList,nameIncrease)
        recyclerAdapter = FriendListAdapter(this@ExpenseActivity,friendsList)
        binding.recyclerList.layoutManager = layoutManager
        binding.recyclerList.adapter = recyclerAdapter
        binding.btnSplitEqual.setOnClickListener {
            if(checker(binding.etCost.text.toString().trim(),binding.etDesc.text.toString().trim())) {
                val intent = Intent(this@ExpenseActivity, FinalActivity::class.java)
                intent.putExtra("ListOfFriends", recyclerAdapter.checkList)
                intent.putExtra("TotalCost", binding.etCost.text.toString().trim().toDouble())
                intent.putExtra("descBill", binding.etDesc.text.toString().trim())
                intent.putExtra("Flag",true)
                startActivity(intent)
            }
        }
        binding.btnSplitUnequal.setOnClickListener {
            if(checker(binding.etCost.text.toString().trim(),binding.etDesc.text.toString().trim())) {
                val intent = Intent(this@ExpenseActivity, FinalActivity::class.java)
                intent.putExtra("ListOfFriends", recyclerAdapter.checkList)
                intent.putExtra("TotalCost", binding.etCost.text.toString().trim().toDouble())
                intent.putExtra("descBill", binding.etDesc.text.toString().trim())
                intent.putExtra("Flag",false)
                startActivity(intent)
            }
        }
        binding.btnAddFrnd.setOnClickListener {
            addFriend()
        }
    }

    private fun addFriend() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        val myLayout = LinearLayout(this)
        myLayout.orientation = LinearLayout.VERTICAL

        alertDialog.setTitle("Add Friend")
        alertDialog.setMessage("Add Unique Names Only")
        alertDialog.setCancelable(false)

        val input1 = EditText(this)
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20F,this.resources.displayMetrics).toInt()
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(px,0,px,0)
        input1.layoutParams = lp
        myLayout.addView(input1)
        alertDialog.setView(myLayout)
        alertDialog.setPositiveButton("Cancel") { _, _ -> }
        alertDialog.setNegativeButton("Add") { _, _ ->
            try {
                val name = input1.text.toString()
                if(name.isEmpty()) {
                    Toast.makeText(this, "Input a valid friend name", Toast.LENGTH_LONG).show()
                } else {
                    var getter = DBAsyncTaskFriend(
                        applicationContext,
                        FriendEntity(name, 0.00),
                        1
                    ).execute().get()
                    if(getter) {
                        Toast.makeText(this, "Friend already exist, use another name", Toast.LENGTH_LONG).show()
                    } else {
                        getter = DBAsyncTaskFriend(
                            applicationContext,
                            FriendEntity(
                                name,
                                0.00
                            ),
                            2
                        ).execute().get()
                        if(getter) {
                            Toast.makeText(this, "Added Friend : $name", Toast.LENGTH_LONG).show()
                            overridePendingTransition(0,0)
                            val inte = Intent(this,ExpenseActivity::class.java)
                            inte.putExtra("Money",binding.etCost.text.toString().trim())
                            inte.putExtra("descBill",binding.etDesc.text.toString().trim())
                            inte.putExtra("isMoney",true)
                            startActivity(inte)
                            overridePendingTransition(0,0)
                            finish()
                        } else {
                            Toast.makeText(this, "Friend not added! Try again", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: RuntimeException) {
                Toast.makeText(this,"Can't Add Friend, Try Again", Toast.LENGTH_LONG).show()
            }
        }
        alertDialog.show()
    }

    private fun checker(sa: String,sb: String): Boolean {
        return when {
            sa.isEmpty() -> {
                binding.etCost.error = "Required"
                binding.etCost.requestFocus()
                false
            }
            sa.toDouble()==0.00 -> {
                binding.etCost.error = "Enter valid cost"
                binding.etCost.requestFocus()
                false
            }
            sb.isEmpty() -> {
                binding.etDesc.error = "Required"
                binding.etDesc.requestFocus()
                false
            }
            else -> true
        }
    }

}
