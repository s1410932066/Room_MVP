package com.example.room_mvp.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.room_mvp.RoomDataBase.MyData;

import java.util.List;

//設置操作資料庫SQL語法的DAO-Interface
@Dao
public interface DataUao {

    String tableName = "MyTable";
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)    //預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertData(MyData myData);

    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<MyData> displayAll();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + tableName +" WHERE name = :name")
    List<MyData> findDataByName(String name);

    /**簡易更新資料的方法*/
    @Update
    void updateData(MyData myData);

    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(MyData myData);

}
