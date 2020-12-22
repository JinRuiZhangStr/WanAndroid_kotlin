package com.zjrdev.wanandroid.data.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *Created by 张金瑞.
 *Data: 2020-12-22
 */
@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id:Int =0,
    @ColumnInfo(name = "user_icon")
    val icon: String?= null,
    @ColumnInfo(name = "user_publice_name")
    val publicName: String?= null,
    @ColumnInfo(name = "user_name")
    val name: String?= null
): Serializable