package com.example.room_mvp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room_mvp.RoomDataBase.DataBase;
import com.example.room_mvp.RoomDataBase.MyData;
import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Contract.IView{

    MyAdapter myAdapter;
    MyData nowSelectedData;
    RecyclerView recyclerView;
    Presenter presenter;
    EditText edName, edPhone, edHobby, edElseInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);//設置資料庫監視

        presenter = new Presenter(this,this);

        Button btCreate = findViewById(R.id.button_Create);
        Button btModify = findViewById(R.id.button_Modify);
        Button btClear = findViewById(R.id.button_Clear);
        edName = findViewById(R.id.editText_Name);
        edPhone = findViewById(R.id.editText_Phone);
        edHobby = findViewById(R.id.editText_Hobby);
        edElseInfo = findViewById(R.id.editText_else);
        recyclerView = findViewById(R.id.recyclerView);

        setRecyclerFunction(recyclerView);//設置RecyclerView左滑刪除
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));    //設置分隔線

        //初始化RecyclerView & Recycleview點擊監聽
        presenter.allData();

        //新增資料
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String phone = edPhone.getText().toString();
                String hobby = edHobby.getText().toString();
                String elseInfo = edElseInfo.getText().toString();
                presenter.button_Create(name,phone,hobby,elseInfo);
                Toast.makeText(MainActivity.this, "已新增！" , Toast.LENGTH_LONG).show();
                clear_edit();
            }
        });

        //修改資料
        btModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (nowSelectedData == null) return;    //如果目前沒前台沒有資料，則以下程序不執行
                    int  id = nowSelectedData.getId();
                    String name = edName.getText().toString();
                    String phone = edPhone.getText().toString();
                    String hobby = edHobby.getText().toString();
                    String elseInfo = edElseInfo.getText().toString();
                    presenter.button_Modify(id,name,phone,hobby,elseInfo);
                    clear_edit();
                    nowSelectedData = null;
                    Toast.makeText(MainActivity.this, "已更新資訊！" , Toast.LENGTH_LONG).show();
            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_edit();
                nowSelectedData = null;
            }
        });
    }

    @Override
    public void clear_edit() {
        edName.setText("");
        edPhone.setText("");
        edHobby.setText("");
        edElseInfo.setText("");
    }

    @Override
    public void resetData_view() {
        myAdapter.refreshView();
    }

    @Override
    public void displayData_view(List<MyData> data) {
        myAdapter = new MyAdapter(this, data);
        recyclerView.setAdapter(myAdapter);
        //取得被選中的資料，並顯示於畫面
        myAdapter.setOnItemClickListener((myData) -> {
            nowSelectedData = myData;
            edName.setText(myData.getName());
            edPhone.setText(myData.getPhone());
            edHobby.setText(myData.getHobby());
            edElseInfo.setText(myData.getElseInfo());
        });
    }

    private void setRecyclerFunction(RecyclerView recyclerView){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            //這裡是告訴RecyclerView你想開啟哪些操作
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
            }
            //管理上下拖曳
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            //管理滑動情形
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        myAdapter.deleteData(position);
                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}