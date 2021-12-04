package com.example.bluetooth_app.model.localDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bluetooth_app.model.Member;

import java.util.List;

@Dao
public interface MemberDAO {
    @Insert
    long insert(Member member);

    @Delete
    void delete(Member member);

    @Query("DELETE FROM Member")
    void clear();

    @Query("DELETE FROM Member where mobile_number=:mobile_number ")
    void deleteByMobile_number(String mobile_number);

    @Query("SELECT * FROM Member WHERE mobile_number LIKE :mobile_number")
    Member searchByMobile_number(String mobile_number);

    @Query("SELECT * FROM Member")
    List<Member> selectAll();

}
