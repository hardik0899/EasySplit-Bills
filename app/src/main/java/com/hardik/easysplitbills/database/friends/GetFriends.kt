package com.hardik.easysplitbills.database.friends

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class GetFriends(private val context: Context): AsyncTask<Void, Void, ArrayList<FriendEntity>>() {
    override fun doInBackground(vararg params: Void?): ArrayList<FriendEntity> {
        val send: ArrayList<FriendEntity>
        val db = Room.databaseBuilder(context, FriendDatabase::class.java,"Friends").build()
        send = db.friendDao().getAllFriends() as ArrayList<FriendEntity>
        db.close()
        return send
    }
}