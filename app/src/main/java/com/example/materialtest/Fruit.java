package com.example.materialtest;

/**
 *
 * Created by zzq on 2018/8/14.
 * Fruit类表示了卡片布局的一项
 */

public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
