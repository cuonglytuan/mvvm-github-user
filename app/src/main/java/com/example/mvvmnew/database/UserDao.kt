package com.example.mvvmnew.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmnew.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE sessionId = :sessionId ORDER BY `index`")
    fun getDataSource(sessionId: String): DataSource.Factory<Int, UserEntity>

    @Query("DELETE FROM users WHERE sessionId = :sessionId")
    suspend fun deleteBySession(sessionId: String)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

}