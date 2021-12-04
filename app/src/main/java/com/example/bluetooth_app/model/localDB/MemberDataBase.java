package com.example.bluetooth_app.model.localDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bluetooth_app.model.Member;

@Database(entities = {Member.class},version = 1,exportSchema = false)
public abstract class MemberDataBase extends RoomDatabase  {

        public abstract MemberDAO memberDAO();

}
