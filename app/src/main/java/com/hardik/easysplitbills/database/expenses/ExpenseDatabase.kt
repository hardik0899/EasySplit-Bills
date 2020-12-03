package com.hardik.easysplitbills.database.expenses

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExpenseEntity::class],version = 1,exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}