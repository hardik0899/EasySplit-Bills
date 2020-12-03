package com.hardik.easysplitbills.database.attachs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Attaches")
data class AttachEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "expense_id") val expense_id: String,
    @ColumnInfo(name = "attach_holder") val attachHolder: String,
    @ColumnInfo(name = "attach_share") val attachShare: Double
)