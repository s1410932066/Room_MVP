package com.example.room_mvp;

import com.example.room_mvp.RoomDataBase.MyData;

import java.util.List;

public interface Contract {
    interface IView{
        void clear_edit();
        void resetData_view();
        void displayData_view(List<MyData> data);

    }
    interface IPresenter{
        void button_Create(String name,String phone,String hobby,String elseInfo);
        void button_Modify(int id,String name,String phone,String hobby,String elseInfo);
        void resetData_presenter();
        void allData();
        void displayData_presenter(List<MyData> data);

    }
    interface IModel{
        void insertData(MyData data);
        void updataData(MyData data);
        void getallData();
    }

}
