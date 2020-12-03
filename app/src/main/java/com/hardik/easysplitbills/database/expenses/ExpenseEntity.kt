package com.hardik.easysplitbills.database.expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expenses")
data class ExpenseEntity(
    @PrimaryKey val expense_id: String,
    @ColumnInfo(name = "expense_cost") val expenseCost: Double,
    @ColumnInfo(name = "expense_desc") val epenseDesc: String
)