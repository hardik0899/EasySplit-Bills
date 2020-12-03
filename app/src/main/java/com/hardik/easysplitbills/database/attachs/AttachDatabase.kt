package com.hardik.easysplitbills.database.attachs

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AttachEntity::class],version = 1,exportSchema = false)
abstract class AttachDatabase: RoomDatabase() {
    abstract fun attachDao(): AttachDao
}