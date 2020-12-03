package com.hardik.easysplitbills.database.friends

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Friends")
data class FriendEntity(
    @PrimaryKey val friend_name: String,
    @ColumnInfo(name = "debt") var debt: Double
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(friend_name)
        parcel.writeDouble(debt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendEntity> {
        override fun createFromParcel(parcel: Parcel): FriendEntity {
            return FriendEntity(parcel)
        }

        override fun newArray(size: Int): Array<FriendEntity?> {
            return arrayOfNulls(size)
        }
    }

}
