package com.hardik.easysplitbills.database.attachs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttachDao {
    @Insert
    fun insertAttach(attachEntity: AttachEntity)

    @Query("SELECT * FROM Attaches")
    fun getAllAttaches(): List<AttachEntity>

    @Query("DELETE FROM Attaches")
    fun nukeAllAttaches()
}