package com.example.materialtest;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zzq on 2018/8/14.
 * 作为RecycleView的适配器，实现RecycleView的主要功能
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context mContext;
    private List<Fruit> mFruitList;

    //重写函数，用于创建ViewHold的实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            //设置cardView子项的点击事件
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext,FruitActivity.class);//初始化指向FruitActivity的Intent
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());//放置水果的名字
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());//放置水果的图片
                mContext.startActivity(intent);
            }
        });
        return  holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //对每个RecycleView子项进行赋值，水果的名称和水果的图片
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);//Glide是图片加载库，可以加载高像素的图片，防止传统加载方式造成的内存溢出
    }

    //返回子项的个数
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    //定义的内部类
    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        //ViewHolder的构造函数
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;//将view转换为cardView
            //获取cardview布局的子项ID，对应layout文件夹中的fruit_item.xml
            fruitImage = (ImageView)view.findViewById(R.id.fruit_image);//获取图片的ID
            fruitName = (TextView)view.findViewById(R.id.fruit_name);//获取水果名字的ID
        }
    }

    //FruitAdapter类的构造函数，用于传递要处理的水果列表
    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;
    }



}
