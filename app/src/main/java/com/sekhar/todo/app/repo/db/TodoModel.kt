package com.sekhar.todo.app.repo.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_table")
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    var TodoID : Int = 0,
    var subject : String? = null
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(TodoID)
        parcel.writeString(subject)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoModel> {
        override fun createFromParcel(parcel: Parcel): TodoModel {
            return TodoModel(parcel)
        }

        override fun newArray(size: Int): Array<TodoModel?> {
            return arrayOfNulls(size)
        }
    }

}
