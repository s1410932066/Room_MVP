package com.example.room_mvp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.room_mvp.RoomDataBase.DataBase;
import com.example.room_mvp.RoomDataBase.MyData;

import java.util.List;

public class Model implements Contract.IModel {
    private Context context;
    Contract.IPresenter callback;

    public Model(Contract.IPresenter callback,Context context) {        //callback至IPresneter
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void insertData(MyData data) {

        new Thread(() -> {
            DataBase.getInstance(context).getDataUao().insertData(data);
            ((Activity)context).runOnUiThread(()->{
                callback.resetData_presenter();
            });
        }).start();
    }

    @Override
    public void updataData(MyData data) {
        new Thread(() -> {
            DataBase.getInstance(context).getDataUao().updateData(data);
            ((Activity)context).runOnUiThread(()->{
                callback.resetData_presenter();
            });
        }).start();

    }

    @Override
    public void getallData() {
        new Thread(() -> {
            List<MyData> data = DataBase.getInstance(context).getDataUao().displayAll();
            ((Activity)context).runOnUiThread(()->{
                callback.displayData_presenter(data);
            });
        }).start();
    }

}
