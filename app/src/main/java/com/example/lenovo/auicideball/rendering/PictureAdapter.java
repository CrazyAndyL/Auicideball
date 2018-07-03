package com.example.lenovo.auicideball.rendering;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.auicideball.R;

import java.util.List;

public class PictureAdapter extends  RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    //动态数组
    private List<Picture> mList;

    //构造
    public PictureAdapter(List<Picture> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定行布局
        View view = View.inflate(parent.getContext(), R.layout.layout_person_img,null);
        //实例化ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //设置数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //获取当前实体类对象
        Picture vo = mList.get(position);
        //设置
        holder.img.setImageResource(vo.getmImg());

    }

    //数量
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //内部类
    class ViewHolder extends RecyclerView.ViewHolder{
        //行布局中的控件
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            img=(ImageView) itemView.findViewById(R.id.item_image2);
        }

    }
}