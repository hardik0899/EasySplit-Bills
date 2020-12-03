package com.hardik.easysplitbills.database.friends

import androidx.room.*

@Dao
interface FriendDao {
    @Insert
    fun insertFriend(friendEntity: FriendEntity)

    @Update
    fun updateFriend(friendEntity: FriendEntity)

    @Query("SELECT * FROM Friends WHERE friend_name = :friendName")
    fun getFriendByName(friendName: String): FriendEntity

    @Query("SELECT * FROM Friends")
    fun getAllFriends(): List<FriendEntity>

    @Query("DELETE FROM Friends")
    fun nukeAllFriends()
}