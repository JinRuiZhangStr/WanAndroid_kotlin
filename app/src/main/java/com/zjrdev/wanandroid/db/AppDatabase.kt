package com.zjrdev.wanandroid.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zjrdev.wanandroid.app.WanApplication
import com.zjrdev.wanandroid.data.bean.User

/**
 *Created by 张金瑞.
 *Data: 2020-12-22
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    //获取操作数据库的实例对象
    abstract fun userDao(): UserDao

    //获取本地历史数据
//    abstract fun readHistroyData()
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase().also { INSTANCE = it }
            }

        private fun buildDatabase() =
            Room.databaseBuilder(WanApplication.CONTEXT, AppDatabase::class.java, "user.db").build()
    }
}