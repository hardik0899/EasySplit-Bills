package com.hardik.easysplitbills.database.expenses

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class DBAsyncTaskExpense(context: Context, private val expenseEntity: ExpenseEntity, private val mode: Int) : AsyncTask<Void, Void, Boolean>() {

    private val db = Room
        .databaseBuilder(context, ExpenseDatabase::class.java,"Expenses")
        .build()

    override fun doInBackground(vararg params: Void?): Boolean {
        when(mode) {
            1 -> {
                db.expenseDao().insertExpense(expenseEntity)
                db.close()
                return true
            }
            2 -> {
                db.expenseDao().nukeAllExpenses()
                db.close()
                return true
            }
        }
        return false
    }

}