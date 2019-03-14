package com.github.bassaer.simplebase.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    fun create(user: User)
    @Update
    fun save(user: User)
    @Query("SELECT * FROM user WHERE id = :userId")
    fun findById(userId: Long): User
    @Query("SELECT * FROM user")
    fun findAll(): MutableList<User>
}