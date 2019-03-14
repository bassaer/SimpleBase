package com.github.bassaer.simplebase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false) var id: Long = 0L,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "count") var count:Int
)
