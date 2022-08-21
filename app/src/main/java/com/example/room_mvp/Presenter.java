package com.example.room_mvp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.room_mvp.RoomDataBase.DataBase;
import com.example.room_mvp.RoomDataBase.MyData;

import java.util.List;

public class Presenter implements Contract.IPresenter{

    private Activity activity;
    MyAdapter myAdapter;
    private Context context;
    private final Contract.IView callback;
    private Model model;
    public Presenter(Contract.IView callback,Context context) {     //calldack至IView
        this.callback = callback;
        this.context = context;
        model = new Model(this,context);
    }

    @Override
    public void button_Create(String name, String phone, String hobby, String elseInfo) {
        if (name.length() == 0) {
            return;
        }
        else{
            MyData data = new MyData(name, phone, hobby, elseInfo);
            model.insertData(data);
        }
    }

    @Override
    public void button_Modify(int id, String name, String phone, String hobby, String elseInfo) {
        MyData data = new MyData(id, name , phone , hobby , elseInfo);
        model.updataData(data);
    }

    @Override
    public void resetData_presenter() {
        callback.resetData_view();
    }

    @Override
    public void allData() {
        model.getallData();
    }

    @Override
    public void displayData_presenter(List<MyData> data) {
        callback.displayData_view(data);
    }
}
