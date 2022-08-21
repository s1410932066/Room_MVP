package com.example.room_mvp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.room_mvp.RoomDataBase.DataBase;
import com.example.room_mvp.RoomDataBase.MyData;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyData> myData;
    private Activity activity;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(Activity activity, List<MyData> myData) {
        this.activity = activity;
        this.myData = myData;
    }
    //建立對外接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(android.R.id.text1);
            view = itemView;
        }
    }
    /**更新資料*/
    public void refreshView() {
        new Thread(()->{
            List<MyData> data = DataBase.getInstance(activity).getDataUao().displayAll();
            this.myData = data;
            activity.runOnUiThread(() -> {
                notifyDataSetChanged(); //刷新recyclerview
            });
        }).start();
    }
    /**刪除資料*/
    public void deleteData(int position){
        new Thread(()->{
            DataBase.getInstance(activity).getDataUao().deleteData(myData.get(position));
            activity.runOnUiThread(()->{
                notifyItemRemoved(position);
                refreshView();
            });
        }).start();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(myData.get(position).getName());
        holder.view.setOnClickListener((v)->{
            onItemClickListener.onItemClick(myData.get(position));
        });

    }
    @Override
    public int getItemCount() {
        return myData.size();
    }
    /**建立對外接口*/
    public interface OnItemClickListener {
        void onItemClick(MyData myData);
    }
}
