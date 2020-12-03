package com.hardik.easysplitbills.database.attachs

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class DBAsyncTaskAttach(private val context: Context, private val attachEntity: AttachEntity,private val mode : Int) : AsyncTask<Void, Void, Boolean>() {

    private val db = Room
        .databaseBuilder(context, AttachDatabase::class.java,"Attaches")
        .build()

    override fun doInBackground(vararg params: Void?): Boolean {
        when(mode) {
            1 -> {
                db.attachDao().insertAttach(attachEntity)
                db.close()
                return true
            }
            2 -> {
                db.attachDao().nukeAllAttaches()
                db.close()
                return true
            }
        }
        return false
    }

}