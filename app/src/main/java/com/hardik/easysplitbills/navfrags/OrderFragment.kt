package com.hardik.easysplitbills.navfrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardik.easysplitbills.MainActivity
import com.hardik.easysplitbills.adapters.OrderOuterAdapter
import com.hardik.easysplitbills.database.attachs.AttachEntity
import com.hardik.easysplitbills.database.attachs.GetAttaches
import com.hardik.easysplitbills.database.expenses.ExpenseEntity
import com.hardik.easysplitbills.database.expenses.GetExpenses
import com.hardik.easysplitbills.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var recyclerAdapter: OrderOuterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        val expenseList = GetExpenses((activity as MainActivity).applicationContext).execute().get() as ArrayList<ExpenseEntity>
        val attachList = GetAttaches((activity as MainActivity).applicationContext).execute().get() as ArrayList<AttachEntity>
        expenseList.reverse()
        attachList.reverse()
        val arrayInner1 : ArrayList<ArrayList<String>> = arrayListOf()
        val arrayInner2 : ArrayList<ArrayList<String>> = arrayListOf()
        for(i in 0 until expenseList.size) {
            val temp1 = arrayListOf<String>()
            val temp2 = arrayListOf<String>()
            for(j in 0 until attachList.size) {
                if(expenseList[i].expense_id == attachList[j].expense_id) {
                    temp1.add(attachList[j].attachHolder)
                    temp2.add(attachList[j].attachShare.toString())
                }
            }
            arrayInner1.add(temp1)
            arrayInner2.add(temp2)
        }

        if(expenseList.size == 0 || expenseList.isEmpty()) {
            binding.layDefault.visibility = View.VISIBLE
            binding.mainLayout.visibility = View.GONE
        } else {
            binding.layDefault.visibility = View.GONE
            binding.mainLayout.visibility = View.VISIBLE
            linearLayoutManager = LinearLayoutManager(context)
            recyclerAdapter = OrderOuterAdapter(context!!,expenseList,arrayInner1,arrayInner2)
            binding.recyclerOrder.layoutManager = linearLayoutManager
            binding.recyclerOrder.adapter = recyclerAdapter
        }
        return binding.root
    }

}
