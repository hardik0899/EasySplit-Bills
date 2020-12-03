package com.hardik.easysplitbills.database.attachs

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class GetAttaches(private val context: Context):
    AsyncTask<Void, Void, ArrayList<AttachEntity>>() {
    override fun doInBackground(vararg params: Void?): ArrayList<AttachEntity> {
        val send: ArrayList<AttachEntity>
        val db = Room.databaseBuilder(context, AttachDatabase::class.java,"Attaches").build()
        send = db.attachDao().getAllAttaches() as ArrayList<AttachEntity>
        db.close()
        return send
    }
}