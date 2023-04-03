package com.example.mvvmnew.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmnew.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase(){
    abstract fun getDao() : UserDao
}