package com.example.myapplication.map;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class MapList {

    private ArrayList<int[][]> list;
    private ArrayList<Bitmap> bitmaps;

    public MapList() {
        list = new ArrayList<>();
        bitmaps = new ArrayList<>();
    }

    public ArrayList<int[][]> getList() {
        return list;
    }

    public void setList(ArrayList<int[][]> list) {
        this.list = list;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
}
