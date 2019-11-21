package com.example.myapplication.model;

import android.graphics.Bitmap;

public class ModelMonster {

    private int x;
    private int y;
    private Bitmap bitmap;
    private int type;
    private int mapCount;
    private String content;
    private int width;
    private int height;

    public ModelMonster() {
    }

    public ModelMonster(int x, int y, Bitmap bitmap, int type, int mapCount, String content, int width, int height) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.type = type;
        this.mapCount = mapCount;
        this.content = content;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMapCount() {
        return mapCount;
    }

    public void setMapCount(int mapCount) {
        this.mapCount = mapCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
