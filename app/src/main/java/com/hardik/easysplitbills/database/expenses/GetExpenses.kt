package com.hardik.easysplitbills.database.expenses

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class GetExpenses(private val context: Context):
    AsyncTask<Void, Void, ArrayList<ExpenseEntity>>() {
    override fun doInBackground(vararg params: Void?): ArrayList<ExpenseEntity> {
        val send: ArrayList<ExpenseEntity>
        val db = Room.databaseBuilder(context, ExpenseDatabase::class.java,"Expenses").build()
        send = db.expenseDao().getAllExpenses() as ArrayList<ExpenseEntity>
        db.close()
        return send
    }
}