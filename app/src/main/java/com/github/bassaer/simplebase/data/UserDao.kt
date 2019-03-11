package com.github.bassaer.simplebase.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    fun save(user: User)
    @Query("SELECT * FROM user WHERE id = :userId")
    fun load(userId: Int): LiveData<User>
}