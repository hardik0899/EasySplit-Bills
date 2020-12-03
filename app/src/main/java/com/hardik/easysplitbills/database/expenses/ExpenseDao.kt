package com.hardik.easysplitbills.database.expenses

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM Expenses")
    fun getAllExpenses(): List<ExpenseEntity>

    @Query("DELETE FROM Expenses")
    fun nukeAllExpenses()
}